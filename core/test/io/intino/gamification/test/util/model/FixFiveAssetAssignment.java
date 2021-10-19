package io.intino.gamification.test.util.model;

import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.World;

public class FixFiveAssetAssignment extends MissionAssignment {

    private static String missionId = "FixOneAsset";
    private static int total = 5;
    private static boolean endsWithMatch = true;

    public FixFiveAssetAssignment(World world, String playerId) {
        super(world.id(), world.currentMatch().id(), missionId, playerId, total, null, endsWithMatch);
    }
}
