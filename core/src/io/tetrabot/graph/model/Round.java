package io.tetrabot.graph.model;

import io.tetrabot.util.time.TimeUtils;

import java.time.Instant;

public final class Round extends Node {

    private final NodeCollection<Match> matches;
    private Instant startTime;
    private Instant endTime;
    private State state = State.Created;

    public Round(String id) {
        super(id);
        matches = new NodeCollection<>();
        matches.init(this, Match.class);
    }

    @Override
    void onInit() {
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

    @Override
    public Round copy() {
        Round copy = new Round(id());
        copy.startTime = startTime;
        copy.endTime = endTime;
        copy.state = state;
        matches.stream().map(Match::copy).forEach(copy.matches::add);
        return copy;
    }

    public enum State {
        Created, Running, Finished
    }

}
