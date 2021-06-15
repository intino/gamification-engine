package io.intino.gamification.core.box.logic;

import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;

import java.util.HashMap;
import java.util.Map;

public class CheckerHandler {

    private static final Map<String, Checker<? extends GamificationEvent>> AchievementCheckerMap = new HashMap<>();
    private static final Map<String, Checker<? extends GamificationEvent>> MissionCheckerMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends GamificationEvent> boolean check(Achievement achievement, T event, Player player) {
        try {
            Checker<T> checker = (Checker<T>) AchievementCheckerMap.get(achievement.id());
            return checker.check(event, player);
        } catch(ClassCastException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends GamificationEvent> boolean check(Mission mission, T event, Player player) {
        try {
            Checker<T> checker = (Checker<T>) MissionCheckerMap.get(mission.id());
            return checker.check(event, player);
        } catch(ClassCastException e) {
            return false;
        }
    }

    public static <T extends GamificationEvent> void progressIf(Achievement achievement, Checker<T> checker) {
        AchievementCheckerMap.put(achievement.id(), checker);
    }

    public static <T extends GamificationEvent> void progressIf(Mission mission, Checker<T> checker) {
        MissionCheckerMap.put(mission.id(), checker);
    }

    public interface Checker<T> {
        boolean check(T event, Player player);
    }
}
