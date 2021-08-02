package io.intino.gamification.core.mounter;

import io.intino.gamification.core.Core;

import java.util.HashMap;
import java.util.Map;

public class Mounters {

    private final Core core;

    private static Map<Class<? extends Mounter>, Mounter> builder = new HashMap<>();

    public Mounters(Core core) {
        this.core = core;
        buildMounters();
    }

    private void buildMounters() {
        //builder.put(TimeUtils.class, new TimeUtils(core.configuration().timeZone.get()));
    }

    public <T extends Mounter> T mounter(Class<T> clazz) {
        return (T) builder.get(clazz);
    }
}
