package io.intino.gamification.test.util;

import io.intino.gamification.GamificationConfig;
import io.intino.gamification.GamificationEngine;

import java.util.HashMap;
import java.util.Map;

public class EngineTestHelper {

    public static GamificationEngine getEngine() {

        GamificationEngine engine = new GamificationEngine(new GamificationConfig.Builder()
                .gamificationDatamart("./temp/datamarts/cinepolis-gamification")
                .gamificationPath("./temp/datamarts/cinepolis-gamification")
                .build());
        engine.launch();

        return engine;
    }
}
