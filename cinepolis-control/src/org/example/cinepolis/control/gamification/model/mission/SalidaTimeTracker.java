package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;

public class SalidaTimeTracker extends Mission {

    private static final String ID = "SalidaTimeTracker";
    private static final String DESCRIPTION = "Salida";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 1;

    public SalidaTimeTracker() {
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
