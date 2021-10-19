package org.example.cinepolis.control.gamification.model.mission;


import io.intino.gamification.graph.model.MissionAssignment;

import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.*;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.CategoryValue.Gestion;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.FrequencyValue.Semanal;
import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.PriorityValue.MuyAlta;

@Name("Atención de tickets OTRS")

@Description("El equipo técnico tiene 7 días para dar solución de los tickets de OTRS," +
        " a partir del 7 día de asignación y no dar solución se harán las reducciones de puntos.")

@Priority(MuyAlta)
@Frequency(Semanal)
@Category(Gestion)

@Penalization(day="7", points = 5)
@Penalization(day="8", points = 10)
@Penalization(day="9", points = 20)
@Penalization(day="10", points = 30)
@Penalization(day="11", points = 40)
@Penalization(day="12", points = 50)
@Penalization(day="13", points = 65)
@Penalization(day="14", points = 80)
@Penalization(day="15", points = 100)
public class AtencionTicketsOTRSMission extends CinepolisMission {

    public AtencionTicketsOTRSMission() {
        super(AtencionTicketsOTRSMission.class.getSimpleName());
    }

    @Override
    protected void setProgressCallbacks() {

    }

    public Assignment assignment() {
        return assignment(1);
    }

    public Assignment assignment(int stepsToComplete) {
        return assignment(stepsToComplete, new MissionAssignment.ExpirationTime());
    }

    public Assignment assignment(int stepsToComplete, MissionAssignment.ExpirationTime expirationTime) {
        return new Assignment(id(), stepsToComplete, expirationTime);
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
