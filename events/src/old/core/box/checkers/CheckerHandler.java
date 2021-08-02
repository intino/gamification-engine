package old.core.box.checkers;

import old.core.box.events.GamificationEvent;
import old.core.graph.Achievement;
import old.core.graph.Mission;
import old.core.graph.Player;

import java.util.HashMap;
import java.util.Map;

public class CheckerHandler {

    private static final Map<String, Checker<? extends GamificationEvent>> AchievementCheckerMap = new HashMap<>();
    private static final Map<String, Checker<? extends GamificationEvent>> MissionCheckerMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends GamificationEvent> CheckResult check(Achievement achievement, T event, Player player) {
        try {
            Checker<T> checker = (Checker<T>) AchievementCheckerMap.get(achievement.id());
            return checker.check(event, player);
        } catch(ClassCastException | NullPointerException e) {
            return CheckResult.Skip;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends GamificationEvent> CheckResult check(Mission mission, T event, Player player) {
        try {
            Checker<T> checker = (Checker<T>) MissionCheckerMap.get(mission.id());
            return checker.check(event, player);
        } catch(ClassCastException | NullPointerException e) {
            return CheckResult.Skip;
        }
    }

    public static <T extends GamificationEvent> void progressIf(Achievement achievement, Checker<T> checker) {
        AchievementCheckerMap.put(achievement.id(), checker);
    }

    public static <T extends GamificationEvent> void progressIf(Mission mission, Checker<T> checker) {
        MissionCheckerMap.put(mission.id(), checker);
    }

    public static Checker<? extends GamificationEvent> checkerOf(Achievement achievement) {
        return AchievementCheckerMap.get(achievement.id());
    }

    public interface Checker<T> {
        CheckResult check(T event, Player player);
    }
}
