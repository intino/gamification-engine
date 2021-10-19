package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.graph.model.MissionAssignment;

public class FixOneAssetAssignment extends MissionAssignment {

    private static String missionId = "FixOneAsset";
    private static int total = 1;
    private static boolean endsWithMatch = true;

    public FixOneAssetAssignment(String worldId, String matchId, String playerId) {
        super(worldId, matchId, missionId, playerId, total, null, endsWithMatch);
    }
}
