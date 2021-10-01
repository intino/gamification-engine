package org.example.cinepolis.control.gamification.model.mission;

public class EnvioPlanner extends TimePenaltyMission {

    private static final String ID = "EnvioPlanner";
    private static final String DESCRIPTION = "Planner compartido";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 1;

    public EnvioPlanner() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE, PRIORITY);
    }

    @Override
    protected void setProgressCallbacks() {

    }

    @Override
    protected void initPenaltyMap() {
        penaltyMap.put(4, -50);
        penaltyMap.put(5, -50);
    }
}
