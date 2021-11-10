package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Round extends Node {

    private NodeCollection<Match> matches;
    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);
    //private NodeCollection<ObtainedAchievement> obtainedAchievements;

    public Round(String id) {
        super(id);
    }

    @Override
    void init() {
        this.matches = new NodeCollection<>(absoluteId());
        //this.obtainedAchievements = new NodeCollection<>(competitionId());
    }

    void begin() {
        startTime.set(TimeUtils.currentInstant());
        onBegin();
        state.set(State.Running);
    }

    void end() {
        endTime.set(TimeUtils.currentInstant());
        sealPoints();
        onEnd();
        state.set(State.Finished);
    }

    private void sealPoints() {
        Season season = parent();
        matches.forEach(m -> {
            season.playerStates().find(m.id()).sealFacts(List.copyOf(m.facts));
            m.facts.clear();
        });
    }

    public final NodeCollection<Match> matches() {
        //TODO: Devolver unmodificable
        return matches;
    }

    State state() {
        return state.get();
    }

    @Override
    void destroyChildren() {
        matches.forEach(Node::markAsDestroyed);
        //obtainedAchievements.forEach(Node::markAsDestroyed);
    }

    @Override
    protected Season parent() {
        String[] ids = parentIds();
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

    protected void onBegin() {}
    protected void onEnd() {}

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

        public final int matchScore() {
            return facts.stream().mapToInt(Fact::value).sum();
        }

        public final List<Fact> facts() {
            return Collections.unmodifiableList(facts);
        }

        @Override
        protected Round parent() {
            String[] ids = parentIds();
            return GamificationGraph.get()
                    .competitions().find(ids[0])
                    .seasons().find(ids[1])
                    .rounds().find(ids[2]);
        }
    }
}
