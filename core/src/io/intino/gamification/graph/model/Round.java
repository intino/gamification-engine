package io.intino.gamification.graph.model;

import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;

public final class Round extends Node {

    private NodeCollection<Match> matches;
    private Instant startTime;
    private Instant endTime;
    private State state = State.Created;

    public Round(String id) {
        super(id);
    }

    @Override
    void onInit() {
        if(matches == null) matches = new NodeCollection<>();
        matches.init(this, Match.class);
    }

    void begin() {
        startTime = TimeUtils.now();
        state = State.Running;
    }

    void end() {
        endTime = TimeUtils.now();
        state = State.Finished;
    }

    public NodeCollection<Match> matches() {
        return matches;
    }

    public Instant startTime() {
        return this.startTime;
    }

    public Round startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public Instant endTime() {
        return this.endTime;
    }

    public Round endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public State state() {
        return this.state;
    }

    public Round state(State state) {
        this.state = state;
        return this;
    }

    public Season season() {
        return parent();
    }

    @Override
    public String toString() {
        return "Round{" +
                "id=" + id() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", matches=" + matches.size() +
                '}';
    }

    public enum State {
        Created, Running, Finished
    }

}
