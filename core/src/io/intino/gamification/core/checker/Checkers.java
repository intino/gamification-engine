package io.intino.gamification.core.checker;

import io.intino.gamification.core.Core;
import io.intino.gamification.utils.file.FileUtils;
import io.intino.gamification.utils.time.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class Checkers {

    private final Core core;

    private static Map<Class<? extends Checker>, Checker> builder = new HashMap<>();

    public Checkers(Core core) {
        this.core = core;
        buildCheckers();
    }

    private void buildCheckers() {
        //builder.put(TimeUtils.class, new TimeUtils(core.configuration().timeZone.get()));
    }

    public <T extends Checker> T checker(Class<T> clazz) {
        return (T) builder.get(clazz);
    }
}
