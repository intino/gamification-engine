package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.MissionAssignment;

import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.*;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.CategoryValue.Actividades;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.FrequencyValue.Diaria;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.PriorityValue.Alta;

@Name("Registro de entrada en Time Tracker")
@Description("Registrar la entrada en el sistema de Time Tracker")
@Category(Actividades)
@Frequency(Diaria)
@Priority(Alta)
public class EntradaTimeTracker extends CinepolisMission {

    public EntradaTimeTracker() {
        super(EntradaTimeTracker.class.getSimpleName());
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
