package io.tetrabot.test.util;

import io.tetrabot.TetrabotConfig;
import io.tetrabot.Tetrabot;

public class EngineTestHelper {

    public static Tetrabot getEngine() {

        Tetrabot engine = new Tetrabot(new TetrabotConfig.Builder()
                .gamificationDatamart("./temp/datamarts/cinepolis-gamification")
                .gamificationPath("./temp/datamarts/cinepolis-gamification")
                .build());
        engine.launch();

        return engine;
    }
}
