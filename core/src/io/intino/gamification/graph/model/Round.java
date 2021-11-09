package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Round extends Node {

    private NodeCollection<Match> matches;
    private Instant startTime;
    private Instant endTime;
    private State state = State.Created;

    public Round(String id) {
        super(id);
    }

    @Override
    void init() {
        this.matches = new NodeCollection<>(absoluteId());
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

    public final NodeCollection<Match> matches() {
        return matches;
    }

    void begin() {
        startTime = TimeUtils.currentInstant();
        onBegin();
        state = State.Running;
    }

    void end() {
        endTime = TimeUtils.currentInstant();
        onEnd();
        state = State.Finished;
    }

    protected void onBegin() {}
    protected void onEnd() {}

    @Override
    void destroyChildren() {
        matches.forEach(Node::markAsDestroyed);
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

    public static class Match extends Node {

        private final List<Fact> facts = new ArrayList<>();

        public Match(String playerId) {
            super(playerId);
        }

        public void addFact(Fact fact) {
            facts.add(fact);
        }

        public int score() {
            return facts.stream().mapToInt(Fact::value).sum();
        }

        public List<Fact> facts() {
            return Collections.unmodifiableList(facts);
        }

        public Round round() {
            return parent();
        }

        @Override
        public Round parent() {
            String[] ids = parentIds();
            return GamificationGraph.get()
                    .competitions().find(ids[0])
                    .seasons().find(ids[1])
                    .rounds().find(ids[2]);
        }
    }
}
