package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.graph.structure.ReadOnlyProperty;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.time.TimeUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class Match extends WorldNode {

    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);
    private final Map<String, PlayerState> players;
    private final Map<String, ActorState> npcs;

    public Match(String worldId, String id) {
        this(worldId, id, new HashMap<>());
    }

    public Match(String worldId, String id, Map<String, Match.PlayerState> persistencePlayerState) {
        super(worldId, id);
        this.players = clean(persistencePlayerState);
        this.npcs = new HashMap<>();
    }

    private Map<String, PlayerState> clean(Map<String, PlayerState> persistencePlayerState) {
        for (PlayerState playerState : persistencePlayerState.values()) {
            playerState.score(0);
        }
        return persistencePlayerState;
    }

    void begin() {
        startTime.set(TimeUtils.currentInstant());
        onBegin();
        notifyEntities(e -> e.onMatchBegin(this));
        state.set(State.Running);
    }

    void end() {
        endTime.set(TimeUtils.currentInstant());

        missionAssignmentsOf(players).forEach(ma -> {
            if(endWithinThisMatch(ma) && ma.progress().state() == InProgress) {
                ma.update(ma.progress().state());
                ma.progress().fail();
            }
        });

        try {
            onEnd();
        } finally {
            notifyEntities(e -> e.onMatchEnd(this));
            state.set(State.Finished);
        }
    }

    private void notifyEntities(Consumer<Entity> routine) {
        world().players().stream().filter(Node::isAvailable).forEach(routine);
        world().npcs().stream().filter(Node::isAvailable).forEach(routine);
        world().items().stream().filter(Node::isAvailable).forEach(routine);
    }

    private List<MissionAssignment> missionAssignmentsOf(Map<String, PlayerState> players) {

        return players.values().stream()
                .filter(ps -> ps.actor() != null)
                .filter(ps -> ps.actor().isAvailable())
                .map(PlayerState::missionAssignments)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    void runMissionProgressTask(MissionProgressTask task) {
        Player player = world().players().find(task.playerId);
        if(player != null && player.isAvailable()) {
            task.execute();
        }
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

    public final Map<String, PlayerState> players() {
        return Collections.unmodifiableMap(players);
    }

    public final Map<String, ActorState> npcs() {
        return Collections.unmodifiableMap(npcs);
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

    public Map<String, PlayerState> persistencePlayerState() {
        return players.values().stream()
                .map(this::filter)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(ps -> ps.actorId, Function.identity()));
    }

    private PlayerState filter(PlayerState playerState) {
        PlayerState newPlayerState = playerState.copy();
        newPlayerState.missionAssignments.removeIf(this::endWithinThisMatch);
        if(newPlayerState.missionAssignments().size() == 0) return null;
        return newPlayerState;
    }

    private boolean endWithinThisMatch(MissionAssignment missionAssignment) {
        return missionAssignment.hasExpired() || missionAssignment.expirationTime().endsWithMatch();
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

        public void assignMission(MissionAssignment missionAssignment) {
            Mission mission = world().missions().find(missionAssignment.missionId());
            if(mission == null) {
                NoSuchElementException e = new NoSuchElementException("Mission " + missionAssignment.missionId() + " not exists");
                Log.error(e.getMessage(), e);
                throw e;
            }

            missionAssignment.worldId(world().id());
            missionAssignment.matchId(id());
            missionAssignment.playerId(actorId);

            // Multiples assignments -> mission??
            if (missionAssignment(missionAssignment.missionId()) == null) {
                missionAssignments.add(missionAssignment);
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

        public void failMission(String missionId) {
            missionAssignments.stream()
                    .filter(ma -> ma.missionId().equals(missionId))
                    .forEach(MissionAssignment::fail);
        }

        public void completeMission(String missionId) {
            missionAssignments.stream()
                    .filter(ma -> ma.missionId().equals(missionId))
                    .forEach(MissionAssignment::complete);
        }

        public void cancelMission(String missionId) {
            missionAssignments.removeIf(ma ->
                    ma.missionId().equals(missionId) && ma.progress().state() == InProgress
            );
        }

        public PlayerState copy() {
            PlayerState ps = new PlayerState(actorId);
            for (MissionAssignment missionAssignment : missionAssignments) {
                ps.missionAssignments.add(missionAssignment.copy());
            }
            return ps;
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
