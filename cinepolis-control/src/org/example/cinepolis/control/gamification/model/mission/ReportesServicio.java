package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;

public class ReportesServicio extends Mission {

    private static final String ID = "ReportesServicio";
    private static final String DESCRIPTION = "Reporte enviado";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 1;

    public ReportesServicio() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE, PRIORITY);
    }

    @Override
    protected void setProgressCallbacks() {

    }

    @Override
    protected void onMissionComplete(MissionAssignment missionAssignment) {
        missionAssignment.playerState().addScore(100);
    }

    @Override
    protected void onMissionFail(MissionAssignment missionAssignment) {
        missionAssignment.playerState().addScore(-100);
    }
}