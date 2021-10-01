package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;

public class EntradaTimeTracker extends Mission {

    private static final String ID = "EntradaTimeTracker";
    private static final String DESCRIPTION = "Entrada";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 1;

    public EntradaTimeTracker() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE, PRIORITY);
    }

    @Override
    protected void setProgressCallbacks() {

    }

    @Override
    protected void onMissionComplete(MissionAssignment missionAssignment) {
        missionAssignment.playerState().addScore(50);
    }

    @Override
    protected void onMissionFail(MissionAssignment missionAssignment) {
        missionAssignment.playerState().addScore(-50);
    }
}
