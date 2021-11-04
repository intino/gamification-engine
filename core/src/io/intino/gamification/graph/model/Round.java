package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Round extends CompetitionNode {

    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);
    //private final NodeCollection<ObtainedAchievement> obtainedAchievements;

    public Round(String id) {
        super(id);
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
        onEnd();
        state.set(State.Finished);
    }

    protected void onBegin() {}
    protected void onEnd() {}

    public enum State {
        Created, Running, Finished
    }

    public class Match {

        private final String playerId;
        private final List<Fact> facts = new ArrayList<>();

        private Match(String playerId) {
            this.playerId = playerId;
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

        @Override
        public Actor actor() {
            Actor actor = super.actor();
            return actor != null ? actor : world().players().find(playerId);
        }



        public List<MissionAssignment> missionAssignments() {
            return Collections.unmodifiableList(missionAssignments);
        }

        public Stream<MissionAssignment> missionAssignmentsOf(String missionId) {
            return missionAssignments.stream().filter(m -> m.missionId().equals(missionId));
        }

        public void failMission(String missionId) {
            missionAssignments.stream()
                    .filter(ma -> ma.missionId().equals(missionId))
                    .forEach(MissionAssignment::fail);
        }

        public void completeMission(String missionId) {
            missionAssignments.stream()
                    .filter(ma -> ma.missionId().equals(missionId))
                    .forEach(MissionAssignment::complete);
        }

        public void cancelMission(String missionId) {
            missionAssignments.removeIf(ma ->
                    ma.missionId().equals(missionId) && ma.progress().state() == InProgress
            );
        }

        */
    }
}
