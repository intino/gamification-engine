package io.intino.gamification.graph.model;

import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;

public final class Season extends Node {

    private transient final NodeCollection<Round> rounds;
    private final NodeCollection<PlayerState> playerStates;
    private Instant startTime;
    private Instant endTime;
    private State state = State.Created;

    public Season(String id) {
        super(id);
        rounds = new NodeCollection<>();
        rounds.init(this, Round.class);
        playerStates = new NodeCollection<>();
        playerStates.init(this, PlayerState.class);
        startTime = TimeUtils.now();
    }

    @Override
    void onInit() {
        competition().players().stream().map(p -> new PlayerState(p.id())).forEach(playerStates::add);
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
        competition().players().forEach(p -> playerStates.add(new PlayerState(p.id())));
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