package io.intino.gamification.graph.model;

import io.intino.gamification.data.Progress;
import io.intino.gamification.data.Property;
import io.intino.gamification.data.ReadOnlyProperty;
import io.intino.gamification.utils.time.TimeUtils;

import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

import static io.intino.gamification.data.Progress.State.InProgress;

public final class Match extends WorldNode {

    public static final long NO_EXPIRATION_TIME = Long.MAX_VALUE;

    private final int instance;
    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);
    private final List<Mission> missions;
    private final Map<String, PlayerState> players;
    private final Map<String, ActorState> npcs;
    private final ExpirationConfig expirationConfig;

    public Match(String world, String id, List<Mission> missions) {
        this(world, id, missions, new ExpirationConfig(false, 0));
    }

    public Match(String world, String id, List<Mission> missions, ExpirationConfig expirationConfig) {
        this(0, world, id, missions, expirationConfig);
    }

    private Match(int instance, String world, String id, List<Mission> missions, ExpirationConfig expirationConfig) {
        super(world, id);
        this.instance = instance;
        this.missions = List.copyOf(missions);
        this.players = new LinkedHashMap<>();
        this.npcs = new LinkedHashMap<>();
        this.expirationConfig = expirationConfig;
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

    public boolean cycle() {
        return this.expirationConfig.cycle;
    }

    public List<Mission> missions() {
        return missions;
    }

    public Mission findMission(String id) {
        return missions.stream().filter(m -> m.id().equals(id)).findFirst().orElse(null);
    }

    public Match copy() {
        final int instance = this.instance + 1;
        final String id = id() + "_" + instance;
        return new Match(instance, worldId(), id, missions, expirationConfig);
    }

    public void begin() {
        startTime.set(TimeUtils.currentInstant());
        notifyEntities(e -> e.onMatchBegin(this));
        state.set(State.Running);
    }

    public void update() {
        notifyEntities(e -> e.onMatchUpdate(this));
        checkMissionsExpiration();
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

    public void end() {
        endTime.set(TimeUtils.currentInstant());
        notifyEntities(e -> e.onMatchEnd(this));
        state.set(State.Finished);
    }

    private void notifyEntities(Consumer<Entity> routine) {
        world().players().stream().forEach(routine);
        world().npcs().stream().forEach(routine);
        world().items().stream().forEach(routine);
    }

    public boolean hasExpired() {
        if(!hasExpirationTime()) return false;
        final Instant now = TimeUtils.currentInstant();
        final Instant expirationTs = startTime().plusSeconds(expirationConfig.expirationTimeSeconds);
        return !now.isBefore(expirationTs);
    }

    public boolean hasExpirationTime() {
        return expirationConfig.expirationTimeSeconds != NO_EXPIRATION_TIME;
    }

    public enum State {
        Created, Running, Finished
    }

    public static class ActorState {

        private final String actorId;
        private long score;

        public ActorState(String actorId) {
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

        public PlayerState(String playerId) {
            super(playerId);
            this.missionAssignments = new ArrayList<>();
        }

        public void assignMission(String missionId) {
            missionAssignments.add(new MissionAssignment(actorId(), missionId));
        }

        public List<MissionAssignment> missionAssignments() {
            return Collections.unmodifiableList(missionAssignments);
        }
    }

    public static class ExpirationConfig {

        private final boolean cycle;
        private final long expirationTimeSeconds;

        public ExpirationConfig(boolean cycle, long expirationTimeSeconds) {
            this.cycle = cycle;
            this.expirationTimeSeconds = expirationTimeSeconds;
        }
    }
}
