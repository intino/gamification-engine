package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.MissionAssignment;

import java.time.Instant;

import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.*;

@Name("Paros de función")
@Description("Atender al paro de función en menos de 20 horas")
@Category(CategoryValue.Actividades)
@Frequency(FrequencyValue.Eventual)
@Priority(PriorityValue.MuyAlta)

@Penalization(hour = "20", points = 10)
@Penalization(hour = "25", points = 20)
@Penalization(hour = "30", points = 30)
@Penalization(hour = "35", points = 45)
@Penalization(hour = "40", points = 60)
@Penalization(hour = "45", points = 75)
@Penalization(hour = "50", points = 100)
public class ParosFuncion extends CinepolisMission {

    public ParosFuncion() {
        super(ParosFuncion.class.getSimpleName());
    }

    @Override
    protected void setProgressCallbacks() {

    }

    public Assignment assignment() {
        return new Assignment(id(), 1, new MissionAssignment.ExpirationTime(Instant.now().plusSeconds(50 * 3600)));
    }

    public static class Assignment extends MissionAssignment {

        protected Assignment(String missionId, int stepsToComplete, ExpirationTime expirationTime) {
            super(missionId, stepsToComplete, expirationTime);
        }

        @Override
        protected MissionAssignment getCopy() {
            return new Assignment(missionId(), progress().total(), expirationTime());
        }
    }

}
