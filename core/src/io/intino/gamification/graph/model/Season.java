package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public final class Season extends Node {

    private transient NodeCollection<Round> rounds;
    private NodeCollection<PlayerState> playerStates;
    private Instant startTime;
    private Instant endTime;
    private State state = State.Created;

    public Season(String id) {
        super(id);
    }

    public Season(String id, List<PlayerState> persistencePlayerState) {
        super(id);
        this.playerStates.addAll(persistencePlayerState);
    }

    @Override
    void onInit() {
        rounds = new NodeCollection<>();
        rounds.init(absoluteId(), Round.class);

        playerStates = new NodeCollection<>();
        playerStates.init(absoluteId(), PlayerState.class);
        competition().players().forEach(player -> playerStates.add(new PlayerState(player.id())));

        startTime = TimeUtils.now();
    }

    public Round currentRound() {
        if(rounds.isEmpty()) return null;
        Round round = rounds.last();
        return round.state() == Round.State.Finished ? null : round;
    }

    public void startNewRound(Round round) {
        if (round != null) {
            rounds.add(round);
            round.begin();
        }
    }

    public void finishCurrentRound() {
        Round currentRound = rounds.last();
        if(currentRound != null) currentRound.end();
    }

    void begin() {
        startTime = TimeUtils.now();
        onBegin();
        state = State.Running;
    }

    void end() {
        endTime = TimeUtils.now();
        state = State.Finished;
    }

    public final NodeCollection<Round> rounds() {
        return rounds;
    }

    public NodeCollection<PlayerState> playerStates() {
        return playerStates;
    }

    public final Instant startTime() {
        return startTime;
    }

    public final Season startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public final Instant endTime() {
        return endTime;
    }

    public final Season endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public final State state() {
        return state;
    }

    public final Season state(State state) {
        this.state = state;
        return this;
    }

    protected void onBegin() {}
    protected void onEnd() {}

    public final Competition competition() {
        return parent();
    }

    @Override
    public final Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
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

    public enum State {
        Created, Running, Finished
    }
}