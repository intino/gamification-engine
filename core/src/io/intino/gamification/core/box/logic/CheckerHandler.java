package io.intino.gamification.core.box.logic;

import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.graph.Achievement;

import java.util.HashMap;
import java.util.Map;

public class CheckerHandler {

    private static final Map<String, Checker<? extends GamificationEvent>> CheckerMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends GamificationEvent> boolean check(Achievement achievement, T event) {
        try {
            Checker<T> checker = (Checker<T>) CheckerMap.get(achievement.id());
            return checker.check(event);
        } catch(ClassCastException e) {
            return false;
        }
    }

    public static <T extends GamificationEvent> void progressIf(Achievement achievement, Checker<T> checker) {
        CheckerMap.put(achievement.id(), checker);
    }

    public interface Checker<T> {
        boolean check(T event);
    }
}
