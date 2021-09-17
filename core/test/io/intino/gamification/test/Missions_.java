package io.intino.gamification.test;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.events.GamificationEvent;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.model.Match;
import io.intino.gamification.test.util.EngineUtils_;
import io.intino.gamification.test.util.events.FixAsset;
import io.intino.gamification.test.util.model.Cinesa;
import io.intino.gamification.test.util.model.FixFiveAsset;
import io.intino.gamification.test.util.model.Workday;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static io.intino.gamification.test.util.model.FixFiveAsset.*;
import static io.intino.gamification.util.data.Progress.State.*;
import static io.intino.gamification.util.time.Scale.Day;
import static io.intino.gamification.util.time.TimeUtils.*;
import static org.junit.Assert.assertEquals;

public class Missions_ {

    private static GamificationEngine engine;
    private static Cinesa world;

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


//        //TODO: Fallar misión a propósito
//        world.players().find("t6").failMission("FixOneAsset");
//        world.players().find("t9").failMission("FixOneAsset");
//
//        //TODO: Completar misión a propósito
//        world.players().find("t7").completeMission("FixOneAsset");
//        world.players().find("t10").completeMission("FixOneAsset");

    @Test
    public void test1() {

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(0, playerState.score());
        assertEquals(0, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(InProgress, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test2() {

        publish(new FixAsset(world.id(), "t1"), 3);

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(0, playerState.score());
        assertEquals(0.6, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(InProgress, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test3() {

        publish(new FixAsset(world.id(), "t1"), 5);

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test4() {

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(DO_NOTHING_SCORE, playerState.score());
        assertEquals(0, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Failed, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test5() {

        publish(new FixAsset(world.id(), "t1"), 3);

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(60, playerState.score());
        assertEquals(0.6, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Failed, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test6() {

        publish(new FixAsset(world.id(), "t1"), 5);

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test7() {

        world.players().find("t1").cancelMission("FixFiveAsset");

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(0, playerState.missionAssignments().size());
        assertEquals(0, playerState.score());
    }

    @Test
    public void test8() {

        publish(new FixAsset(world.id(), "t1"), 3);

        world.players().find("t1").cancelMission("FixFiveAsset");

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(0, playerState.missionAssignments().size());
        assertEquals(0, playerState.score());
    }

    @Test
    public void test9() {

        publish(new FixAsset(world.id(), "t1"), 5);

        world.players().find("t1").cancelMission("FixFiveAsset");

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test10() {

        world.players().find("t1").cancelMission("FixFiveAsset");

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(0, playerState.missionAssignments().size());
        assertEquals(0, playerState.score());
    }

    @Test
    public void test11() {

        publish(new FixAsset(world.id(), "t1"), 3);

        world.players().find("t1").cancelMission("FixFiveAsset");

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(0, playerState.missionAssignments().size());
        assertEquals(0, playerState.score());
    }

    @Test
    public void test12() {

        publish(new FixAsset(world.id(), "t1"), 5);

        world.players().find("t1").cancelMission("FixFiveAsset");

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
    }

    @Test
    public void test13() {

        world.players().find("t1").failMission("FixFiveAsset");

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(FAIL_SCORE, playerState.score());
        assertEquals(0, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Failed, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test14() {

        publish(new FixAsset(world.id(), "t1"), 3);

        world.players().find("t1").failMission("FixFiveAsset");

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(FAIL_SCORE, playerState.score());
        assertEquals(0.6, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Failed, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test15() {

        publish(new FixAsset(world.id(), "t1"), 5);

        world.players().find("t1").failMission("FixFiveAsset");

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test16() {

        world.players().find("t1").failMission("FixFiveAsset");

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(FAIL_SCORE, playerState.score());
        assertEquals(0, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Failed, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test17() {

        publish(new FixAsset(world.id(), "t1"), 3);

        world.players().find("t1").failMission("FixFiveAsset");

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(FAIL_SCORE, playerState.score());
        assertEquals(0.6, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Failed, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test18() {

        publish(new FixAsset(world.id(), "t1"), 5);

        world.players().find("t1").failMission("FixFiveAsset");

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test19() {

        world.players().find("t1").completeMission("FixFiveAsset");

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test20() {

        publish(new FixAsset(world.id(), "t1"), 3);

        world.players().find("t1").completeMission("FixFiveAsset");

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test21() {

        publish(new FixAsset(world.id(), "t1"), 5);

        world.players().find("t1").completeMission("FixFiveAsset");

        Match.PlayerState playerState = world.currentMatch().players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test22() {

        world.players().find("t1").completeMission("FixFiveAsset");

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test23() {

        publish(new FixAsset(world.id(), "t1"), 3);

        world.players().find("t1").completeMission("FixFiveAsset");

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    @Test
    public void test24() {

        publish(new FixAsset(world.id(), "t1"), 5);

        world.players().find("t1").completeMission("FixFiveAsset");

        world.finishCurrentMatch();

        Match.PlayerState playerState = world.finishedMatches().get(0).players().get("t1");

        assertEquals(1, playerState.missionAssignments().size());
        assertEquals(COMPLETE_SCORE, playerState.score());
        assertEquals(1, playerState.missionAssignments().get(0).progress().get(), 0.00001f);
        assertEquals(Complete, playerState.missionAssignments().get(0).progress().state());
    }

    private void publish(GamificationEvent event, int times) {
        for (int i = 0; i < times; i++) {
            engine.eventPublisher().publish(event);
        }
    }
}
