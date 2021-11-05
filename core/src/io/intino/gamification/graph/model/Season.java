package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class Season extends Node {

    private NodeCollection<Round> rounds;
    private NodeCollection<PlayerState> playerStates;
    //private NodeCollection<ObtainedAchievement> obtainedAchievements;
    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);

    public Season(String id) {
        super(id);
    }

    @Override
    void init() {
        this.rounds = new NodeCollection<>(absoluteId());
        this.playerStates = new NodeCollection<>(absoluteId());
        //this.obtainedAchievements = new NodeCollection<>(competitionId());
    }

    public final NodeCollection<Round> rounds() {
        //TODO Devolver unmodifiable
        return rounds;
    }

    public final NodeCollection<PlayerState> playerStates() {
        //TODO Devolver unmodifiable
        return playerStates;
    }

    State state() {
        return state.get();
    }

    @Override
    void destroyChildren() {
        rounds.forEach(Node::markAsDestroyed);
        playerStates.forEach(Node::markAsDestroyed);
        //obtainedAchievements.forEach(Node::markAsDestroyed);
    }

    @Override
    protected Competition parent() {
        String[] ids = parentIds();
        return GamificationGraph.get()
                .competitions().find(ids[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Season season = (Season) o;
        return Objects.equals(rounds, season.rounds) && Objects.equals(playerStates, season.playerStates) && Objects.equals(startTime, season.startTime) && Objects.equals(endTime, season.endTime) && Objects.equals(state, season.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rounds, playerStates, startTime, endTime, state);
    }

    @Override
    public String toString() {
        return "Season{" +
                "rounds=" + rounds +
                ", playerStates=" + playerStates +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                '}';
    }

    /*

    public final void injectPersistencePlayerState(List<PlayerState> persistencePlayerState) {
        this.playerStates().addAll(clean(persistencePlayerState));
    }

    void begin() {
        startTime.set(TimeUtils.currentInstant());
        onBegin();
        state.set(State.Running);
    }

    void end() {
        endTime.set(TimeUtils.currentInstant());
        endMissions();
        onEnd();
        state.set(State.Finished);
    }

    public final void startNewRound(Round round) {

        if (round != null) {
            rounds.add(round);
            round.begin();
        }
    }

    public final void finishCurrentRound() {
        Round currentRound = rounds.last();
        if(currentRound != null && currentRound.isAvailable()) currentRound.end();
    }

    public Round currentRound() {
        if(rounds.isEmpty()) return null;
        Round round = rounds.last();
        return round.state() != Round.State.Finished ? null : round;
    }

    public final List<PlayerState> persistencePlayerState() {
        return playerStates.stream()
                .map(this::filter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void endMissions() {
        playerStates.forEach(PlayerState::endMissions);
    }

    private PlayerState filter(PlayerState playerState) {
        PlayerState newPlayerState = playerState.copy();
        newPlayerState.missionAssignments().removeIf(this::endWithinThisSeason);
        if(newPlayerState.missionAssignments().size() == 0) return null;
        return newPlayerState;
    }

    boolean endWithinThisSeason(MissionAssignment missionAssignment) {
        return missionAssignment.hasExpired() || missionAssignment.expirationTime().endsWithMatch();
    }

    private List<PlayerState> clean(List<PlayerState> persistencePlayerState) {
        persistencePlayerState.forEach(PlayerState::clearFacts);
        return persistencePlayerState;
    }



    protected void onBegin() {}
    protected void onEnd() {}*/

    public enum State {
        Created, Running, Finished
    }
}