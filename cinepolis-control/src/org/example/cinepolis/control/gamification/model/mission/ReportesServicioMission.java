package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.MissionAssignment;

import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.*;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.CategoryValue.Actividades;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.FrequencyValue.Diaria;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.PriorityValue.MuyAlta;

@Name("Reporte de servicio")
@Description("Envío del reporte de servicio")
@Category(Actividades)
@Frequency(Diaria)
@Priority(MuyAlta)
public class ReportesServicioMission extends CinepolisMission {

    public ReportesServicioMission() {
        super(ReportesServicioMission.class.getSimpleName());
    }

    @Override
    protected void setProgressCallbacks() {

    }

    public Assignment assignment() {
        return new Assignment(id(), 1, new MissionAssignment.ExpirationTime());
    }

    public static class Assignment extends MissionAssignment {

        protected Assignment(String missionId, int stepsToComplete, ExpirationTime expirationTime) {
            super(missionId, stepsToComplete, expirationTime);
        }

        @Override
        protected void onMissionComplete() {
            playerState().addScore(CinepolisMission.maxPointsOf((CinepolisMission) mission()));
        }

        @Override
        protected void onMissionFail() {
            playerState().addScore(-CinepolisMission.maxPointsOf((CinepolisMission) mission()));
        }

        @Override
        protected MissionAssignment getCopy() {
            return new Assignment(missionId(), progress().total(), expirationTime());
        }
    }
}
