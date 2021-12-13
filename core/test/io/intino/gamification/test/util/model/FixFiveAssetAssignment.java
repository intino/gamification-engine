package io.intino.gamification.test.util.model;

import io.intino.gamification.graph.model.MissionAssignment;

public class FixFiveAssetAssignment extends MissionAssignment {

    private static String missionId = "FixOneAsset";
    private static int total = 5;
    private static boolean endsWithMatch = true;

    public FixFiveAssetAssignment() {
        super("id", missionId, total, null);
    }

    @Override
    protected MissionAssignment getCopy() {
        return null;
    }
}
