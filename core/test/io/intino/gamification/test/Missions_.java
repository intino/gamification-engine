package io.intino.gamification.test;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.model.PlayerState;
import io.intino.gamification.graph.model.Round;
import io.intino.gamification.graph.model.Season;
import io.intino.gamification.test.util.EngineTestHelper;
import io.intino.gamification.test.util.model.*;
import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.data.Progress.State;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.intino.gamification.test.util.model.FixFiveAsset.*;
import static org.junit.Assert.assertEquals;

@RunWith(org.junit.runners.Parameterized.class)
public class Missions_ {

    private static final float EPSILON = 0.00001f;
    private static final boolean DONT_FINISH_MATCH = false;
    private static final boolean FINISH_MATCH = true;
    private static final Missions_.Action NO_ACTION = null;
    private static final Progress.State NO_STATE = null;

    private static GamificationEngine engine;
    private static Cinesa competition;

    private Season season;
    private Round.Match match;
    private PlayerState playerState;

    private final int nFixAsset;
    private final boolean finishMatch;
    private final Action action;
    private final int score;
    private final float progress;
    private final State state;

    public Missions_(int nFixAsset, boolean finishMatch, Missions_.Action action, int score, float progress, Progress.State state) {
        this.nFixAsset = nFixAsset;
        this.finishMatch = finishMatch;
        this.action = action;
        this.score = score;
        this.progress = progress;
        this.state = state;
    }

    @Parameterized.Parameters
    public static Object[][] cases() {
        return new Object[][] {
                {0, DONT_FINISH_MATCH, NO_ACTION, 0, 0f, Progress.State.InProgress},
                {3, DONT_FINISH_MATCH, NO_ACTION, 0, 0.6f, Progress.State.InProgress},
                {5, DONT_FINISH_MATCH, NO_ACTION, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, FINISH_MATCH, NO_ACTION, DO_NOTHING_SCORE, 0f, Progress.State.Failed},
                {3, FINISH_MATCH, NO_ACTION, 60, 0.6f, Progress.State.Failed},
                {5, FINISH_MATCH, NO_ACTION, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, DONT_FINISH_MATCH, Missions_.Action.Cancel, 0, 0, NO_STATE},
                {3, DONT_FINISH_MATCH, Missions_.Action.Cancel, 0, 0, NO_STATE},
                {5, DONT_FINISH_MATCH, Missions_.Action.Cancel, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, FINISH_MATCH, Missions_.Action.Cancel, 0, 0, NO_STATE},
                {3, FINISH_MATCH, Missions_.Action.Cancel, 0, 0, NO_STATE},
                {5, FINISH_MATCH, Missions_.Action.Cancel, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, DONT_FINISH_MATCH, Missions_.Action.Fail, FAIL_SCORE, 0f, Progress.State.Failed},
                {3, DONT_FINISH_MATCH, Missions_.Action.Fail, FAIL_SCORE, 0.6f, Progress.State.Failed},
                {5, DONT_FINISH_MATCH, Missions_.Action.Fail, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, FINISH_MATCH, Missions_.Action.Fail, FAIL_SCORE, 0f, Progress.State.Failed},
                {3, FINISH_MATCH, Missions_.Action.Fail, FAIL_SCORE, 0.6f, Progress.State.Failed},
                {5, FINISH_MATCH, Missions_.Action.Fail, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, DONT_FINISH_MATCH, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {3, DONT_FINISH_MATCH, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {5, DONT_FINISH_MATCH, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, FINISH_MATCH, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {3, FINISH_MATCH, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {5, FINISH_MATCH, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete}
        };
    }

    @Before
    public void before() {
        engine = EngineTestHelper.getEngine();
        GamificationGraph graph = engine.graph();

        competition = new Cinesa("competition");
        graph.competitions().add(competition);

        FixFiveAsset mission = new FixFiveAsset();
        competition.missions().add(mission);

        Technician technician = new Technician("technician");
        competition.players().add(technician);

        competition.startNewSeason(new Workday("season"));
        season = competition.currentSeason();
        if(season == null) throw new RuntimeException("El season es null");

        season.startNewRound(new Round("round"));

        competition.players().forEach(p -> p.assignMission(new FixFiveAssetAssignment()));
    }

    @Test
    public void execute() {
       try {

           setup();

           checkMissionAssignment();
           assertFinalScoreMatchesExpectedScore();

       } catch (AssertionError e) {
           AssertionError error = new AssertionError("Test " + this + " failed: " + e.getMessage());
           error.setStackTrace(e.getStackTrace());
           throw error;
       }
    }

    private void assertFinalScoreMatchesExpectedScore() {
        assertEquals(score, match.score());
    }

    private void checkMissionAssignment() {

        if(action == Action.Cancel && state != Progress.State.Complete) {
            assertThereAreNoMissionAssignmentsLeft(playerState);
        } else {
            assertMissionAssignmentIsStillPresent(playerState);
        }

        if(action != Missions_.Action.Cancel || state == Progress.State.Complete) {
            assertProgressMatchesExpectedProgress(playerState);
            assertFinalStateMatchesExpected(playerState);
        }
    }

    private void assertThereAreNoMissionAssignmentsLeft(PlayerState playerState) {
        assertEquals(0, playerState.missionAssignments().size());
    }

    private void assertMissionAssignmentIsStillPresent(PlayerState playerState) {
        assertEquals(1, playerState.missionAssignments().size());
    }

    private void assertProgressMatchesExpectedProgress(PlayerState playerState) {
        assertEquals(progress, playerState.missionAssignments().find("FixAsset").progress().get(), EPSILON);
    }

    private void assertFinalStateMatchesExpected(PlayerState playerState) {
        assertEquals(state, playerState.missionAssignments().find("FixAsset").progress().state());
    }

    private void setup() {

        for (int i = 0; i < nFixAsset; i++) {
            //MissionProgressEventManager.get().call(competition, "FixAsset", "", "technician");
        }

        runAction();

        getFinalModel();
    }

    private void getFinalModel() {
        if(finishMatch) competition.finishCurrentSeason();
        playerState = season.playerStates().find("technician");
        match = season.rounds().find("round").matches().find("technician");
    }

    private void runAction() {
        if(action != null) {
            switch (action) {
                case Cancel:
                    competition.players().find("technician").cancelMission("FixFiveAsset");
                    break;
                case Complete:
                    competition.players().find("technician").completeMission("FixFiveAsset");
                    break;
                case Fail:
                    competition.players().find("technician").failMission("FixFiveAsset");
                    break;
            }
        }
    }

    public enum Action {
        Cancel, Complete, Fail
    }

    @Override
    public String toString() {
        return "{" +
                "nFixAsset=" + nFixAsset +
                ", finishMatch=" + finishMatch +
                ", action=" + action +
                ", score=" + score +
                ", progress=" + progress +
                ", state=" + state +
                '}';
    }
}