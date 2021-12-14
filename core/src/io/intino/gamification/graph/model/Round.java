package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.Objects;

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
        matches = new NodeCollection<>();
        matches.init(absoluteId(), Match.class);
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
    public Season parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0])
                .seasons().find(ids[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Round round = (Round) o;
        return Objects.equals(matches, round.matches) && Objects.equals(startTime, round.startTime) && Objects.equals(endTime, round.endTime) && Objects.equals(state, round.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), matches, startTime, endTime, state);
    }

    @Override
    public String toString() {
        return "Round{" +
                "matches=" + matches +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                '}';
    }

    public enum State {
        Created, Running, Finished
    }

}
