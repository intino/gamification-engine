package io.intino.gamification.test.util.model;

import io.intino.gamification.graph.model.Mission;

public class FixFiveAsset extends Mission {

    private static final String ID = "FixFiveAsset";
    private static final String DESCRIPTION = "Arregla cinco proyectores";
    private static final int STEPS_TO_COMPLETE = 5;
    private static final int PRIORITY = 0;
    public static final int COMPLETE_SCORE = 100;
    public static final int FAIL_SCORE = -50;
    public static final int DO_NOTHING_SCORE = -25;

    public FixFiveAsset() {
        super(ID, DESCRIPTION, PRIORITY);
    }
}