package io.intino.gamification.test.util;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.test.util.model.Asset;
import io.intino.gamification.test.util.model.Cinesa;
import io.intino.gamification.test.util.model.Technician;

import java.util.HashMap;
import java.util.List;
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

    public static void initTechnician(Cinesa world, String technicianId, List<String> assetIds) {
        Technician technician = new Technician(world.id(), technicianId);

        for (String assetId : assetIds) {
            Asset asset = new Asset(world.id(), assetId);
            world.items().add(asset);
            technician.inventory().add(asset);
        }

        world.players().add(technician);
    }
}
