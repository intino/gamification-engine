package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.util.time.Scale;

public class ParosFuncion extends TimePenaltyMission {

    private static final String ID = "ParosFuncion";
    private static final String DESCRIPTION = "Horas de atención";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 1;

    public ParosFuncion() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE, PRIORITY);
        scale = Scale.Hour;
    }

    @Override
    protected void setProgressCallbacks() {

    }

    @Override
    protected void initPenaltyMap() {
        penaltyMap.put(20, -10);
        addPenaltyBetween(21, 25, 10);
        addPenaltyBetween(26, 30, 10);
        addPenaltyBetween(31, 40, 10);
        addPenaltyBetween(41, 50, 10);
        penaltyMap.put(51, -50);
    }
}
