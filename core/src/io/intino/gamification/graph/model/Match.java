package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.graph.structure.ReadOnlyProperty;
import io.intino.gamification.util.Logger;
import io.intino.gamification.util.time.Crontab;
import io.intino.gamification.util.time.TimeUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.intino.gamification.util.time.Crontab.Type.Undefined;

public class Match extends WorldNode {

    private final String idBase;
    private final int instance;
    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);
    private final Map<String, PlayerState> players;
    private final Map<String, ActorState> npcs;
    private final Crontab crontab;
    private transient Queue<MissionProgressTask> missionProgressTasks;

    public Match(String worldId, String id) {
        this(worldId, id, Crontab.undefined());
    }

    public Match(String worldId, String id, Crontab crontab) {
        this(0, worldId, id, id, crontab);
    }

    protected Match(int instance, String worldId, String idBase, String id, Crontab crontab) {
        super(worldId, id);
        this.idBase = idBase;
        this.instance = instance;
        this.players = new HashMap<>();
        this.npcs = new HashMap<>();
        this.crontab = crontab;
    }

    void begin() {
        startTime.set(TimeUtils.currentInstant());
        onBegin();
        notifyEntities(e -> e.onMatchBegin(this));
        state.set(State.Running);
    }

    @Override
    void preUpdate() {
        runMissionProgressTasks();
    }

    @Override
    void updateChildren() {
        notifyEntities(e -> e.onMatchUpdate(this));
    }

    @Override
    void postUpdate() {
        checkMissionsExpiration();
    }

    void end() {
        endTime.set(TimeUtils.currentInstant());

        synchronized (this) {
            missionAssignmentsOf(players).forEach(MissionAssignment::fail);
        }

        try {
            onEnd();
        } finally {
            notifyEntities(e -> e.onMatchEnd(this));
            state.set(State.Finished);
        }
    }

    private void runMissionProgressTasks() {
        while (!missionProgressTasks.isEmpty()) {
            MissionProgressTask task = missionProgressTasks.poll();
            Player player = world().players().find(task.playerId);
            if(player != null && player.isAvailable()) {
                task.execute();
            }
        }
    }

    private void notifyEntities(Consumer<Entity> routine) {
        world().players().stream().filter(Node::isAvailable).forEach(routine);
        world().npcs().stream().filter(Node::isAvailable).forEach(routine);
        world().items().stream().filter(Node::isAvailable).forEach(routine);
    }

    private void checkMissionsExpiration() {
        synchronized (this) {
            missionAssignmentsOf(players).forEach(MissionAssignment::checkExpiration);
        }
    }

    private List<MissionAssignment> missionAssignmentsOf(Map<String, PlayerState> players) {

        return players.values().stream()
                .filter(ps -> ps.actor() != null)
                .filter(ps -> ps.actor().isAvailable())
                .map(PlayerState::missionAssignments)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    Match copy() {
        final int instance = this.instance + 1;
        final String id = idBase + "_" + instance;
        return newInstance(instance, world().id(), idBase, id, crontab);
    }

    protected Match newInstance(int instance, String worldId, String idBase, String id, Crontab crontab) {
        return new Match(instance, worldId, idBase, id, crontab);
    }

    boolean hasExpired() {
        if(!hasExpirationTime()) return false;
        return crontab.matches(startTime.get());
    }

    private boolean hasExpirationTime() {
        return crontab.type() != Undefined;
    }

    void addMissionProgressTask(MissionProgressTask task) {
        missionProgressTasks.add(task);
    }

    public final Instant startTime() {
        return startTime.get();
    }

    public final ReadOnlyProperty<Instant> startTimeProperty() {
        return startTime;
    }

    public final Instant endTime() {
        return endTime.get();
    }

    public final ReadOnlyProperty<Instant> endTimeProperty() {
        return endTime;
    }

    public final State state() {
        return state.get();
    }

    public final ReadOnlyProperty<State> stateProperty() {
        return state;
    }

    public final List<PlayerState> players() {
        return new ArrayList<>(players.values());
    }

    public final List<ActorState> npcs() {
        return new ArrayList<>(npcs.values());
    }

    public final PlayerState player(String playerId) {
        synchronized (this) {
            return players.computeIfAbsent(playerId, PlayerState::new);
        }
    }

    public final ActorState npc(String npcId) {
        synchronized (this) {
            return npcs.computeIfAbsent(npcId, ActorState::new);
        }
    }

    Crontab crontab() {
        return crontab;
    }

    @Override
    void initTransientAttributes() {
        this.missionProgressTasks = new ArrayDeque<>();
    }

    protected void onBegin() {}

    protected void onEnd() {}

    protected final void onCreate() {}
    protected final void onDestroy() {}

    public enum State {
        Created, Running, Finished
    }

    public class ActorState implements Serializable {

        protected final String actorId;
        private long score;

        private ActorState(String actorId) {
            this.actorId = actorId;
            this.score = 0;
        }

        public Actor actor() {
            return world().npcs().find(actorId);
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

    public class PlayerState extends ActorState {

        private final List<MissionAssignment> missionAssignments;

        private PlayerState(String playerId) {
            super(playerId);
            this.missionAssignments = new ArrayList<>();
        }

        @Override
        public Actor actor() {
            Actor actor = super.actor();
            return actor != null ? actor : world().players().find(actorId);
        }

        void assignMission(String missionId) {
            Mission mission = world().missions().find(missionId);
            if(mission == null) {
                NoSuchElementException e = new NoSuchElementException("Mission " + missionId + " not exists");
                Logger.error(e);
                throw e;
            }

            if (missionAssignment(missionId) == null) {
                missionAssignments.add(new MissionAssignment(world().id(), mission.id(), actorId, mission.total()));
            }
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
}
