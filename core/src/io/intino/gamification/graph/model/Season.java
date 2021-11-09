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
    private Instant startTime;
    private Instant endTime;
    private State state = State.Created;

    public Season(String id) {
        super(id);
    }

    @Override
    void init() {
        this.rounds = new NodeCollection<>(absoluteId());
        this.playerStates = new NodeCollection<>(absoluteId());
        this.startTime = Instant.now();
    }

    public final NodeCollection<Round> rounds() {
        return rounds;
    }

    public final NodeCollection<PlayerState> playerStates() {
        return playerStates;
    }

    public final State state() {
        return state;
    }

    public Season state(State state) {
        this.state = state;
        return this;
    }

    public Instant startTime() {
        return startTime;
    }

    public Season startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public Instant endTime() {
        return endTime;
    }

    public Season endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    @Override
    void destroyChildren() {
        rounds.forEach(Node::markAsDestroyed);
        playerStates.forEach(Node::markAsDestroyed);
    }

    public Competition competition() {
        return parent();
    }

    @Override
    public Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0]);
    }

    void begin() {
        startTime = TimeUtils.currentInstant();
        onBegin();
        state = State.Running;
    }

    void end() {
        endTime = TimeUtils.currentInstant();
        endMissions();
        onEnd();
        state = State.Finished;
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

    protected void onBegin() {}
    protected void onEnd() {}

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

    public enum State {
        Created, Running, Finished
    }
}