package io.intino.gamification.core.box.checkers;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.mounter.*;

import java.util.HashMap;
import java.util.Map;

public class Checkers {

    private final CoreBox box;

    private static Map<Class<? extends Checker>, Checker> builder = new HashMap<>();

    public Checkers(CoreBox box) {
        this.box = box;
        buildCheckers();
    }

    private void buildCheckers() {
        builder.put(AchievementChecker.class, new AchievementChecker(box));
        builder.put(MissionChecker.class, new MissionChecker(box));
    }

    public <T extends Checker> T checker(Class<T> clazz) {
        return (T) builder.get(clazz);
    }
}
