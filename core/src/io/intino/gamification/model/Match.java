package io.intino.gamification.model;

import io.intino.gamification.data.Property;
import io.intino.gamification.data.ReadOnlyProperty;

import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

public final class Match extends WorldNode {

    private final int instance;
    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);
    private final List<Mission> missions;
    private final Map<String, PlayerState> players;
    private final Map<String, ActorState> npcs;
    private final boolean cycle;

    public Match(String world, String id, List<Mission> missions) {
        this(world, id, missions, false);
    }

    public Match(String world, String id, List<Mission> missions, boolean cycle) {
        this(0, world, id, missions, cycle);
    }

    private Match(int instance, String world, String id, List<Mission> missions, boolean cycle) {
        super(world, id);
        this.instance = instance;
        this.missions = List.copyOf(missions);
        this.players = new LinkedHashMap<>();
        this.npcs = new LinkedHashMap<>();
        this.cycle = cycle;
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
        return this.cycle;
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
        return new Match(instance, worldId(), id, missions, cycle);
    }

    public void begin() {
        startTime.set(Instant.now());
        notifyEntities(e -> e.onMatchBegin(this));
        state.set(State.Running);
    }

    public void update() {
        notifyEntities(e -> e.onMatchUpdate(this));
        updateMissionAssignmentsState();
    }

    private void updateMissionAssignmentsState() {
        // TODO...
    }

    public void end() {
        endTime.set(Instant.now());
        notifyEntities(e -> e.onMatchEnd(this));
        state.set(State.Finished);
    }

    private void notifyEntities(Consumer<Entity> routine) {
        world().players().stream().forEach(routine);
        world().npcs().stream().forEach(routine);
        world().items().stream().forEach(routine);
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
}
