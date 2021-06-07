package io.intino.gamification.core.box.logic;

import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.Player;

import java.util.HashMap;
import java.util.Map;

public class CheckerHandler {

    private static final Map<String, Checker<? extends GamificationEvent>> CheckerMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends GamificationEvent> boolean check(Achievement achievement, T event, Player player) {
        try {
            Checker<T> checker = (Checker<T>) CheckerMap.get(achievement.id());
            return checker.check(event, player);
        } catch(ClassCastException e) {
            return false;
        }
    }

    public static <T extends GamificationEvent> void progressIf(Achievement achievement, Checker<T> checker) {
        CheckerMap.put(achievement.id(), checker);
    }

    public interface Checker<T> {
        boolean check(T event, Player player);
    }
}
