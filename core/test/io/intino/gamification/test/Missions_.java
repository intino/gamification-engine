package io.intino.gamification.test;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.model.Round;
import io.intino.gamification.test.util.EngineTestHelper;
import io.intino.gamification.test.util.events.FixAsset;
import io.intino.gamification.test.util.model.Cinesa;
import io.intino.gamification.test.util.model.FixFiveAsset;
import io.intino.gamification.test.util.model.FixFiveAssetAssignment;
import io.intino.gamification.test.util.model.Workday;
import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.data.Progress.State;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

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
    private static Cinesa world;

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
        world = new Cinesa("world");
        graph.createCompetition(world);

        FixFiveAsset mission = new FixFiveAsset();
        world.missions().add(mission);

        EngineTestHelper.initTechnician(world, "t1", Arrays.asList("a1", "a2"));

        world.startNewSeason(new Workday("match"));

        world.players().forEach(p -> p.assignMission(new FixFiveAssetAssignment()));
    }

    @Test
    public void execute() {
       try {

           Round.Match match = setup();
           checkMissionAssignment(match);
           assertFinalScoreMatchesExpectedScore(match);

       } catch (AssertionError e) {
           AssertionError error = new AssertionError("Test " + this + " failed: " + e.getMessage());
           error.setStackTrace(e.getStackTrace());
           throw error;
       }
    }

    private void assertFinalScoreMatchesExpectedScore(Round.Match match) {
        assertEquals(score, match.score());
    }

    private void checkMissionAssignment(Round.Match match) {

        if(action == Action.Cancel && state != Progress.State.Complete) {
            assertThereAreNoMissionAssignmentsLeft(match);
        } else {
            assertMissionAssignmentIsStillPresent(match);
        }

        if(action != Missions_.Action.Cancel || state == Progress.State.Complete) {
            assertProgressMatchesExpectedProgress(match);
            assertFinalStateMatchesExpected(match);
        }
    }

    private void assertThereAreNoMissionAssignmentsLeft(Round.Match match) {
        assertEquals(0, match.missionAssignments().size());
    }

    private void assertMissionAssignmentIsStillPresent(Round.Match match) {
        assertEquals(1, match.missionAssignments().size());
    }

    private void assertProgressMatchesExpectedProgress(Round.Match match) {
        assertEquals(progress, match.missionAssignments().get(0).progress().get(), EPSILON);
    }

    private void assertFinalStateMatchesExpected(Round.Match match) {
        assertEquals(state, match.missionAssignments().get(0).progress().state());
    }

    private Round.Match setup() {

        for (int i = 0; i < nFixAsset; i++) {
            engine.eventPublisher().publish(new FixAsset(world.id(), "t1"));
        }

        runAction();

        return getFinalPlayerState();
    }

    private Round.Match getFinalPlayerState() {
        Round.Match match;
        if(finishMatch) {
            world.finishCurrentMatch();
            match = world.finishedMatches().find("match").players().get("t1");
        } else {
            match = world.currentSeason().players().get("t1");
        }
        return match;
    }

    private void runAction() {
        if(action != null) {
            switch (action) {
                case Cancel:
                    world.players().find("t1").cancelMission("FixFiveAsset");
                    break;
                case Complete:
                    world.players().find("t1").completeMission("FixFiveAsset");
                    break;
                case Fail:
                    world.players().find("t1").failMission("FixFiveAsset");
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