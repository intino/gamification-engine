package io.intino.gamification.test.util.model;

import io.intino.gamification.graph.model.MissionAssignment;

import java.time.Instant;
import java.util.function.Function;

public class FixFiveAssetAssignment extends MissionAssignment {

    private static String missionId = "FixOneAsset";
    private static int total = 5;
    private static boolean endsWithMatch = true;

    public FixFiveAssetAssignment() {
        //TODO
        super(missionId, total, null, instant -> 100);
    }

    @Override
    protected MissionAssignment getCopy() {
        return null;
    }
}
