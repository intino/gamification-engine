package io.intino.gamification.graph.model;

import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.List;

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
        if(rounds == null) rounds = new NodeCollection<>();
        rounds.init(this, Round.class);

        if(playerStates == null) playerStates = new NodeCollection<>();
        playerStates.init(this, PlayerState.class);
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
            finishCurrentRound();
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
        state = State.Running;
    }

    void end() {
        endTime = TimeUtils.now();
        state = State.Finished;
    }

    public NodeCollection<Round> rounds() {
        return rounds;
    }

    public NodeCollection<PlayerState> playerStates() {
        return playerStates;
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

    public State state() {
        return state;
    }

    public Season state(State state) {
        this.state = state;
        return this;
    }

    public Competition competition() {
        return parent();
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", currentRound=" + currentRound() +
                ", rounds=" + rounds.size() +
                ", playerStates=" + playerStates.size() +
                '}';
    }

    public enum State {
        Created, Running, Finished
    }
}