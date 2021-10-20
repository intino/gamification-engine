package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.util.time.TimeUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.*;

@Name("Envío del planner")
@Description("Envío del planner semanal, entre los días jueves y sábado")
@Category(CategoryValue.Gestion)
@Frequency(FrequencyValue.Semanal)
@Priority(PriorityValue.MuyAlta)

@Penalization(day = "3", points = 50)
@Penalization(day = "4", points = 100)
public class EnvioPlannerMission extends CinepolisMission {

    public EnvioPlannerMission() {
        super(EnvioPlannerMission.class.getSimpleName());
    }

    @Override
    protected void setProgressCallbacks() {

    }

    public Assignment assignment() {
        return new Assignment(id(), 1, nextSunday());
    }

    private MissionAssignment.ExpirationTime nextSunday() {
        LocalDate now = LocalDate.now();
        if(now.getDayOfWeek() != DayOfWeek.THURSDAY) throw new IllegalStateException("Day must be thursday");
        return new MissionAssignment.ExpirationTime(TimeUtils.toInstant(now.plusDays(4)));
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
