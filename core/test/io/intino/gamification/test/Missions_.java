package io.intino.gamification.test;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.model.Match;
import io.intino.gamification.test.util.EngineUtils_;
import io.intino.gamification.test.util.events.FixAsset;
import io.intino.gamification.test.util.model.Cinesa;
import io.intino.gamification.test.util.model.FixFiveAsset;
import io.intino.gamification.test.util.model.Workday;
import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.data.Progress.State;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static io.intino.gamification.test.util.model.FixFiveAsset.*;
import static io.intino.gamification.util.time.Scale.Day;
import static io.intino.gamification.util.time.TimeUtils.*;
import static org.junit.Assert.assertEquals;

@RunWith(org.junit.runners.Parameterized.class)
public class Missions_ {

    private static GamificationEngine engine;
    private static Cinesa world;

    private final Integer nFixAsset;
    private final Boolean finishMatch;
    private final Action action;
    private final Integer score;
    private final Float progress;
    private final State state;

    public Missions_(Integer nFixAsset, Boolean finishMatch, Missions_.Action action, Integer score, Float progress, Progress.State state) {
        this.nFixAsset = nFixAsset;
        this.finishMatch = finishMatch;
        this.action = action;
        this.score = score;
        this.progress = progress;
        this.state = state;
    }

    @Parameterized.Parameters
    public static Object[][] cases() {
        return new Object[][]{
                {0, false, null, 0, 0f, Progress.State.InProgress},
                {3, false, null, 0, 0.6f, Progress.State.InProgress},
                {5, false, null, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, true, null, DO_NOTHING_SCORE, 0f, Progress.State.Failed},
                {3, true, null, 60, 0.6f, Progress.State.Failed},
                {5, true, null, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, false, Missions_.Action.Cancel, 0, null, null},
                {3, false, Missions_.Action.Cancel, 0, null, null},
                {5, false, Missions_.Action.Cancel, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, true, Missions_.Action.Cancel, 0, null, null},
                {3, true, Missions_.Action.Cancel, 0, null, null},
                {5, true, Missions_.Action.Cancel, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, false, Missions_.Action.Fail, FAIL_SCORE, 0f, Progress.State.Failed},
                {3, false, Missions_.Action.Fail, FAIL_SCORE, 0.6f, Progress.State.Failed},
                {5, false, Missions_.Action.Fail, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, true, Missions_.Action.Fail, FAIL_SCORE, 0f, Progress.State.Failed},
                {3, true, Missions_.Action.Fail, FAIL_SCORE, 0.6f, Progress.State.Failed},
                {5, true, Missions_.Action.Fail, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, false, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {3, false, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {5, false, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {0, true, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {3, true, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete},
                {5, true, Missions_.Action.Complete, COMPLETE_SCORE, 1f, Progress.State.Complete}
        };
    }

    @Before
    public void before() {
        engine = EngineUtils_.getEngine();
        GamificationGraph graph = engine.graph();
        world = new Cinesa("world");
        graph.createWorld(world);

        FixFiveAsset mission = new FixFiveAsset();
        world.missions().add(mission);

        EngineUtils_.initTechnician(world, "t1", Arrays.asList("a1", "a2"));

        world.startNewMatch(new Workday("world", "match"));

        world.players().forEach(p -> p.assignMission("FixFiveAsset", truncateTo(nextInstant(currentInstant(), Day), Day)));
    }

    @Test
    public void execute(){

        for (int i = 0; i < nFixAsset; i++) {
            engine.eventPublisher().publish(new FixAsset(world.id(), "t1"));
        }

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

        Match.PlayerState playerState;

        if(finishMatch) {
            world.finishCurrentMatch();
            playerState = world.finishedMatches().find("match").players().get("t1");
        } else {
            playerState = world.currentMatch().players().get("t1");
        }

        if(action == Missions_.Action.Cancel && state != Progress.State.Complete) {
            assertEquals(0, playerState.missionAssignments().size());
        } else {
            assertEquals(1, playerState.missionAssignments().size());
        }

        assertEquals(score.intValue(), playerState.score());

        if(action != Missions_.Action.Cancel || state == Progress.State.Complete) {
            assertEquals(progress, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
            assertEquals(state, playerState.missionAssignments().get(0).progress().state());
        }
    }

    public enum Action {
        Cancel, Complete, Fail
    }
}