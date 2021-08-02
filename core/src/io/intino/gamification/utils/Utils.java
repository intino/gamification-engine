package io.intino.gamification.utils;

import io.intino.gamification.core.Core;
import io.intino.gamification.utils.file.FileUtils;
import io.intino.gamification.utils.time.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    private final Core core;

    private static Map<Class<? extends Util>, Util> builder = new HashMap<>();

    public Utils(Core core) {
        this.core = core;
        buildUtils();
    }

    private void buildUtils() {
        builder.put(TimeUtils.class, new TimeUtils(core.configuration().timeZone.get()));
        builder.put(FileUtils.class, new FileUtils());
    }

    public <T extends Util> T util(Class<T> clazz) {
        return (T) builder.get(clazz);
    }
}
