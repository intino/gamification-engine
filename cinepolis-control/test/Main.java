import io.intino.gamification.GamificationEngine;
import io.intino.gamification.graph.GamificationGraph;
import util.events.FixAsset;
import util.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("gamification_time_zone", "Atlantic/Canary");
        parameters.put("gamification_path", "./gamification");
        parameters.put("gamification_saving_cron", "0 0/1 * 1/1 * ? *");

        GamificationEngine engine = new GamificationEngine(parameters);
        engine.launch();

        /* ----------------------------------------------------------------- */

        Cinema world = createWorld();

        world.currentMatch(new Workday("world", "match"));

        //TODO: EL CURRENT MATCH DEBE ESTAR INICIALIZADO
        Thread.sleep(3000);

        world.players().forEach(p -> p.assignMission("FixOneAsset"));

        engine.eventPublisher()
                .publish(new FixAsset(world.id(), "t1"))
                .publish(new FixAsset(world.id(), "t3"));

        deleteTechnician(world, "t5");
    }

    private static Cinema createWorld() {
        Cinema world = new Cinema("world");

        FixOneAsset mission = new FixOneAsset();
        world.missions().add(mission);
        BeginTwoMatches achievement = new BeginTwoMatches();
        world.achievements().add(achievement);

        initTechnician(world, "t1", Arrays.asList("a1", "a2"));
        initTechnician(world, "t2", Arrays.asList("a3", "a4"));
        initTechnician(world, "t3", Arrays.asList("a5", "a6"));
        initTechnician(world, "t4", Arrays.asList("a7", "a8"));
        initTechnician(world, "t5", Arrays.asList("a9", "a10"));

        GamificationGraph.get().worlds().add(world);

        return world;
    }

    private static void initTechnician(Cinema world, String technicianId, List<String> assetIds) {
        Technician technician = new Technician(world.id(), technicianId);

        for (String assetId : assetIds) {
            Asset asset = new Asset(world.id(), assetId);
            world.items().add(asset);
            technician.inventory().add(asset);
        }

        world.players().add(technician);
    }

    private static void deleteTechnician(Cinema world, String technicianId) {
        world.players().destroy(world.players().find(technicianId));
    }
}
