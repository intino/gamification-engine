package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.structure.Fact;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.function.Function;

import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.*;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.CategoryValue.Actividades;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.FrequencyValue.Diaria;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.PriorityValue.Alta;

@Name("Registro de entrada en Time Tracker")
@Description("Registrar la entrada en el sistema de Time Tracker")
@Category(Actividades)
@Frequency(Diaria)
@Priority(Alta)
public class EntradaTimeTrackerMission extends CinepolisMission {

    public EntradaTimeTrackerMission() {
        super(EntradaTimeTrackerMission.class.getSimpleName());
    }

    @Override
    protected void setProgressCallbacks() {

    }

    public Assignment assignment() {
        return new Assignment(id(), 1, new MissionAssignment.ExpirationTime());
    }

    public static class Assignment extends MissionAssignment {

        protected Assignment(String missionId, int stepsToComplete, ExpirationTime expirationTime) {
            super(missionId, stepsToComplete, expirationTime, new Function<Instant, Integer>() {
                @Override
                public Integer apply(Instant instant) {
                    return 100;
                }
            });
        }

        @Override
        protected void onMissionComplete() {
            //TODO: Revisar asignacion de puntos
            playerState().addFact(new Fact(TimeUtils.now(), Fact.Type.Mission, id(), score()));
        }

        @Override
        protected void onMissionFail() {
            playerState().addFact(new Fact(TimeUtils.now(), Fact.Type.Mission, id(), score()));
        }

        @Override
        protected MissionAssignment getCopy() {
            return new Assignment(id(), progress().total(), expirationTime());
        }
    }
}
