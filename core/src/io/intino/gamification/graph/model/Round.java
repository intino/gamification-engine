package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Round extends CompetitionNode {

    private final String seasonId;
    private final NodeCollection<Match> matches = new NodeCollection<>();
    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);
    //private final NodeCollection<ObtainedAchievement> obtainedAchievements;

    public Round(String id, String seasonId) {
        super(id);
        this.seasonId = seasonId;
    }

    @Override
    void init() {
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
        Season season = season();
        matches.forEach(m -> {
            season.playerStates().find(m.id()).sealFacts(List.copyOf(m.facts));
            m.facts.clear();
        });
    }

    Season season() {
        return competition().seasons().find(seasonId);
    }

    public final NodeCollection<Match> matches() {
        return matches;
    }

    State state() {
        return state.get();
    }

    protected void onBegin() {}
    protected void onEnd() {}

    public enum State {
        Created, Running, Finished
    }

    public static class Match extends CompetitionNode {

        private final List<Fact<Integer>> facts = new ArrayList<>();

        Match(String playerId) {
            super(playerId);
        }

        void addFact(Fact<Integer> fact) {
            facts.add(fact);
        }

        public int matchScore() {
            return facts.stream().mapToInt(Fact::getValue).sum();
        }

        /*public Actor actor() {
            return world().npcs().find(playerId);
        }

        public long score() {
            return score;
        }

        public void score(long score) {
            this.score = score;
        }

        public void addScore(long delta) {
            this.score += delta;
        }


        public Stream<MissionAssignment> missionAssignmentsOf(String missionId) {
            return missionAssignments.stream().filter(m -> m.missionId().equals(missionId));
        }

        */
    }
}
