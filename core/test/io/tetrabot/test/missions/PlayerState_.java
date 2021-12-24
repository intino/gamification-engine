package io.tetrabot.test.missions;

import io.tetrabot.graph.TetrabotGraph;
import io.tetrabot.graph.model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.tetrabot.util.data.Progress.State.Complete;
import static io.tetrabot.util.data.Progress.State.InProgress;
import static org.junit.Assert.assertEquals;

public class PlayerState_ {

    private static TetrabotGraph graph;
    private static Competition competition;
    private static Player player;
    private static Mission mission;
    private static Season season;
    private static Round round;

    @BeforeClass
    public static void beforeClass() throws Exception {
        graph = new TetrabotGraph();
        competition = new Competition("c");
        graph.competitions().add(competition);
        player = new Player("p");
        competition.players().add(player);
        mission = new Mission("m", 100);
        competition.missions().add(mission);
        season = new Season("s");
        competition.startNewSeason(season);
        round = new Round("r");
        season.startNewRound(round);
    }

    @Test
    public void shouldRemoveFromActiveMissionsAndAddToFinishedMissionsWhenMissionIsNoLongerInProgress() {

        MissionAssignment assignment = new MissionAssignment("m1", "m", 1, null);

        assertEquals(InProgress, assignment.state());

        player.assignMission(assignment);

        assertEquals(1, player.state().activeMissions().size());
        assertEquals(0, player.state().finishedMissions().size());

        player.updateMission(assignment);

        assertEquals(Complete, assignment.state());

        assertEquals(0, player.state().activeMissions().size());
        assertEquals(1, player.state().finishedMissions().size());
    }
}
