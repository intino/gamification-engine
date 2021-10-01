package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.util.time.Scale;

import java.util.HashMap;
import java.util.Map;

import static io.intino.gamification.util.time.Scale.Day;

public abstract class TimePenaltyMission extends Mission {

    protected int points = 100;
    protected Scale scale = Day;

    protected final Map<Integer, Integer> penaltyMap;

    public TimePenaltyMission(String id, String description, int stepsToComplete, int priority) {
        super(id, description, stepsToComplete, priority);
        this.penaltyMap = new HashMap<>();
        initPenaltyMap();
    }

    protected abstract void initPenaltyMap();

    public int points() {
        return points;
    }

    public Scale scale() {
        return scale;
    }

    public Integer penaltyOf(Integer stamp) {
        return penaltyMap.get(stamp);
    }

    @Override
    protected void onMissionComplete(MissionAssignment missionAssignment) {
        missionAssignment.playerState().addScore(points);
    }

    protected void addPenaltyBetween(int startDay, int finishDay, int points) {
        int days = finishDay - startDay + 1;
        for (int i = startDay; i <= finishDay; i++) {
            penaltyMap.put(i, Math.round(points / (float) days));
        }
    }
}
