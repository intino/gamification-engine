package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;

public abstract class CinesaMission extends Mission {

    public CinesaMission(String id, String description, int stepsToComplete, int priority) {
        super(id, description, stepsToComplete, priority);
    }

    public abstract int maxPoints();

    public abstract int dailyPenalty();

    @Override
    protected void onProgressChange(MissionAssignment missionAssignment, Integer oldCurrent, Integer newCurrent) {
        if(newCurrent > oldCurrent) {
            missionAssignment.playerState().addScore(newPercentage(missionAssignment.progress().total(), oldCurrent, newCurrent) * maxPoints());
        }
    }

    private long newPercentage(Integer total, Integer oldCurrent, Integer newCurrent) {
        return (long) ((newCurrent - oldCurrent) / ((float) total));
    }
}
