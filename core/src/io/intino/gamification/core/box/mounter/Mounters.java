package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;

import java.util.HashMap;
import java.util.Map;

public class Mounters {

    private final CoreBox box;

    private static Map<Class<? extends  Mounter>, Mounter> builder = new HashMap<>();

    public Mounters(CoreBox box) {
        this.box = box;
        buildMounters();
    }

    private void buildMounters() {
        builder.put(AchievementMounter.class, new AchievementMounter(box));
    }

    public <T extends Mounter> T mounter(Class<T> clazz) {
        return (T) builder.get(clazz);
    }
}
