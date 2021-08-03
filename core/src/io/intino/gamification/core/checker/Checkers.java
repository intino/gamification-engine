package io.intino.gamification.core.checker;

import io.intino.gamification.core.GamificationCore;

import java.util.HashMap;
import java.util.Map;

public class Checkers {

    private final GamificationCore core;

    private static Map<Class<? extends Checker>, Checker> builder = new HashMap<>();

    public Checkers(GamificationCore core) {
        this.core = core;
        buildCheckers();
    }

    private void buildCheckers() {
        builder.put(TimeChecker.class, new TimeChecker(core));
    }

    public <T extends Checker> T checker(Class<T> clazz) {
        return (T) builder.get(clazz);
    }
}
