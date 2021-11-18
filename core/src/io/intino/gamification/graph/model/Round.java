package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.structure.Fact;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Round extends Node {

    private final NodeCollection<Match> matches = new NodeCollection<>();
    private Instant startTime;
    private Instant endTime;
    private State state = State.Created;

    public Round(String id) {
        super(id);
    }

    @Override
    void init() {
        matches.init(absoluteId());
    }

    @Override
    void destroyChildren() {
        matches.forEach(Node::markAsDestroyed);
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

    public final NodeCollection<Match> matches() {
        //TODO: Revisar usos
        return matches.asReadOnly();
    }

    public final Instant startTime() {
        return this.startTime;
    }

    public final Round startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public final Instant endTime() {
        return this.endTime;
    }

    public final Round endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public final State state() {
        return this.state;
    }

    public final Round state(State state) {
        this.state = state;
        return this;
    }

    protected void onBegin() {}
    protected void onEnd() {}

    public final Season season() {
        return parent();
    }

    @Override
    public final Season parent() {
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

        void addFact(Fact fact) {
            facts.add(fact);
        }

        public final List<Fact> facts() {
            return Collections.unmodifiableList(facts);
        }

        public final int score() {
            return facts.stream().mapToInt(Fact::value).sum();
        }

        public final Round round() {
            return parent();
        }

        @Override
        public final Round parent() {
            String[] ids = parentIds();
            return GamificationGraph.get()
                    .competitions().find(ids[0])
                    .seasons().find(ids[1])
                    .rounds().find(ids[2]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Match match = (Match) o;
            return Objects.equals(facts, match.facts);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), facts);
        }

        @Override
        public String toString() {
            return "Match{" +
                    "facts=" + facts +
                    '}';
        }
    }
}
