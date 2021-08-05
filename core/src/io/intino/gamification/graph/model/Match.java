package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.data.Property;
import io.intino.gamification.util.data.ReadOnlyProperty;
import io.intino.gamification.util.time.Crontab;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

import static io.intino.gamification.util.data.Progress.State.InProgress;
import static io.intino.gamification.util.time.Crontab.Type.Undefined;

public class Match extends WorldNode {

    private final int instance;
    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);
    private final List<Mission> missions;
    private final Map<String, PlayerState> players;
    private final Map<String, ActorState> npcs;
    private final Crontab crontab;
    private final Queue<MissionProgressTask> missionProgressTasks;

    public Match(String world, String id, List<Mission> missions) {
        this(world, id, missions, null);
    }

    public Match(String world, String id, List<Mission> missions, Crontab crontab) {
        this(0, world, id, missions, crontab);
    }

    private Match(int instance, String world, String id, List<Mission> missions) {
        this(instance, world, id, missions, null);
    }

    private Match(int instance, String world, String id, List<Mission> missions, Crontab crontab) {
        super(world, id);
        this.instance = instance;
        this.missions = List.copyOf(missions);
        this.players = new HashMap<>();
        this.npcs = new HashMap<>();
        this.crontab = crontab;
        this.missionProgressTasks = new ArrayDeque<>();
    }

    void begin() {
        startTime.set(TimeUtils.currentInstant());
        onBegin();
        notifyEntities(e -> e.onMatchBegin(this));
        state.set(State.Running);
    }

    void update() {
        runMissionProgressTasks();
        onUpdate();
        notifyEntities(e -> e.onMatchUpdate(this));
        checkMissionsExpiration();
    }

    private void runMissionProgressTasks() {
        while (!missionProgressTasks.isEmpty()) {
            MissionProgressTask task = missionProgressTasks.poll();
            Player player = world().players().find(task.playerId);
            if(player != null && player.enabled()) {
                task.execute();
            }
        }
    }

    private void notifyEntities(Consumer<Entity> routine) {
        world().players().stream().filter(Entity::enabled).forEach(routine);
        world().npcs().stream().filter(Entity::enabled).forEach(routine);
        world().items().stream().filter(Entity::enabled).forEach(routine);
    }

    private void checkMissionsExpiration() {
        players.values().forEach(ps -> ps.missionAssignments().forEach(this::check));
    }

    private void check(MissionAssignment missionAssignment) {
        Mission mission = findMission(missionAssignment.missionId());
        boolean hasExpired = mission.hasExpired(missionAssignment.creationTime());
        if(hasExpired) {
            Progress progress = missionAssignment.progress();
            if(progress.state() == InProgress) progress.fail();
        }
    }

    void end() {
        endTime.set(TimeUtils.currentInstant());
        onEnd();
        notifyEntities(e -> e.onMatchEnd(this));
        state.set(State.Finished);
    }

    Match copy() {
        final int instance = this.instance + 1;
        final String id = id() + "_" + instance;
        return new Match(instance, worldId(), id, missions, crontab);
    }

    boolean hasExpired() {
        if(!hasExpirationTime()) return false;
        return crontab.matches(TimeUtils.currentInstant());
    }

    private boolean hasExpirationTime() {
        return crontab.type() != Undefined;
    }

    void addMissionProgressTask(MissionProgressTask task) {
        missionProgressTasks.add(task);
    }

    public Instant startTime() {
        return startTime.get();
    }

    public ReadOnlyProperty<Instant> startTimeProperty() {
        return startTime;
    }

    public Instant endTime() {
        return endTime.get();
    }

    public ReadOnlyProperty<Instant> endTimeProperty() {
        return endTime;
    }

    public State state() {
        return state.get();
    }

    public ReadOnlyProperty<State> stateProperty() {
        return state;
    }

    public List<Mission> missions() {
        return missions;
    }

    public Mission findMission(String id) {
        return missions.stream().filter(m -> m.id().equals(id)).findFirst().orElse(null);
    }

    public PlayerState player(String playerId) {
        return players.computeIfAbsent(playerId, PlayerState::new);
    }

    public ActorState npc(String npcId) {
        return npcs.computeIfAbsent(npcId, ActorState::new);
    }

    Crontab crontab() {
        return crontab;
    }

    public enum State {
        Created, Running, Finished
    }

    public static class ActorState {

        private final String actorId;
        private long score;

        private ActorState(String actorId) {
            this.actorId = actorId;
            this.score = 0;
        }

        public String actorId() {
            return actorId;
        }

        public long score() {
            return score;
        }

        public void score(long score) {
            this.score = score;
        }

        public void addScore(long delta) {
            this.score += delta;
        }
    }

    public static class PlayerState extends ActorState {

        private final List<MissionAssignment> missionAssignments;

        private PlayerState(String playerId) {
            super(playerId);
            this.missionAssignments = new ArrayList<>();
        }

        public MissionAssignment assignMission(String missionId, int total) {
            MissionAssignment missionAssignment = new MissionAssignment(actorId(), missionId, total);
            missionAssignments.add(missionAssignment);
            return missionAssignment;
        }

        public List<MissionAssignment> missionAssignments() {
            return Collections.unmodifiableList(missionAssignments);
        }

        public MissionAssignment missionAssignment(String missionId) {
            return missionAssignments.stream()
                    .filter(ma -> ma.missionId().equals(missionId))
                    .findFirst().orElse(null);
        }
    }

    static abstract class MissionProgressTask {

        private final String playerId;

        protected MissionProgressTask(String playerId) {
            this.playerId = playerId;
        }

        abstract void execute();
    }

    protected void onBegin() {}
    protected void onUpdate() {}
    protected void onEnd() {}
}
