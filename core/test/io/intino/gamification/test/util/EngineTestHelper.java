package io.intino.gamification.test.util;

import io.intino.gamification.GamificationEngine;

import java.util.HashMap;
import java.util.Map;

public class EngineTestHelper {

    public static GamificationEngine getEngine() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("gamification_time_zone", "Atlantic/Canary");
        parameters.put("gamification_path", "./temp/datamarts/cinepolis-gamification");

        GamificationEngine engine = new GamificationEngine(parameters);
        engine.launch();

        return engine;
    }
}
