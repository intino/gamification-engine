package io.intino.gamification.core.box.helper;

import io.intino.gamification.core.box.CoreBox;

import java.util.HashMap;
import java.util.Map;

public class Helpers {

    private final CoreBox box;

    private static Map<Class<? extends Helper>, Helper> builder = new HashMap<>();

    public Helpers(CoreBox box) {
        this.box = box;
        buildHelpers();
    }

    private void buildHelpers() {
        builder.put(AchievementStateHelper.class, new AchievementStateHelper(box));
        builder.put(MissionStateHelper.class, new MissionStateHelper(box));
        builder.put(MissionHelper.class, new MissionHelper(box));
    }

    public <T extends Helper> T helper(Class<T> clazz) {
        return (T) builder.get(clazz);
    }
}
