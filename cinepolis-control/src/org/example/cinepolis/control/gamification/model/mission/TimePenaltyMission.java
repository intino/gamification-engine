//package org.example.cinepolis.control.gamification.model.mission;
//
//import io.intino.gamification.graph.model.Mission;
//import io.intino.gamification.graph.model.MissionAssignment;
//import io.intino.gamification.util.time.Scale;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static io.intino.gamification.util.time.Scale.Day;
//
//public abstract class TimePenaltyMission extends Mission {
//
//    protected final int maxScore;
//    protected Scale scale = Day;
//
//    protected final Map<Integer, Float> penaltyMap;
//
//    public TimePenaltyMission(String id, String description, int maxScore) {
//        super(id);
//        this.maxScore = maxScore;
//        this.penaltyMap = new HashMap<>();
//        initPenaltyMap();
//    }
//
//    public TimePenaltyMission(String id, String description, int maxScore, int stepsToComplete, int priority) {
//        super(id);
//        this.maxScore = maxScore;
//        this.penaltyMap = new HashMap<>();
//        initPenaltyMap();
//    }
//
//    protected abstract void initPenaltyMap();
//
//    public int maxScore() {
//        return maxScore;
//    }
//
//    public Scale scale() {
//        return scale;
//    }
//
//    public int penaltyOf(Integer stamp) {
//        final float percentage = penaltyMap.getOrDefault(stamp, 0.0f);
//        return -Math.round(percentage * maxScore);
//    }
//
//    @Override
//    protected void onMissionComplete() {
//        missionAssignment.playerState().addScore(maxScore);
//    }
//
//    protected void addPenaltyBetween(int startDay, int finishDay, int points) {
//        int days = finishDay - startDay + 1;
//        for (int i = startDay; i <= finishDay; i++) {
//            penaltyMap.put(i, points / (float) days);
//        }
//    }
//}
