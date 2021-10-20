import io.intino.alexandria.logger.Logger;
import io.intino.gamification.GamificationEngine;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.Log;
import org.example.cinepolis.control.gamification.model.mission.AtencionTicketsOTRSMission;
import util.events.FixAsset;
import util.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("gamification_time_zone", "Atlantic/Canary");
        parameters.put("gamification_path", "./gamification");

        Log.LoggerInstance.set(getLogger());

        GamificationEngine engine = new GamificationEngine(parameters);
        engine.launch();

        /* ----------------------------------------------------------------- */

        Cinesa world = createWorld();

        world.startNewMatch(new Workday("world", "match"));

        AtencionTicketsOTRSMission m = new AtencionTicketsOTRSMission();
        AtencionTicketsOTRSMission.Assignment assignment = m.assignment(1);

        System.out.println(m);

        world.players().find("").assignMission(assignment);

        //world.players().forEach(p -> p.assignMission("FixOneAsset", truncateTo(nextInstant(currentInstant(), Day), Day), true));

        engine.eventPublisher()
//                .publish(new FixAsset(world.id(), "t1"))
//                .publish(new FixAsset(world.id(), "t3"))
//                .publish(new FixAsset(world.id(), "t6"))
//                .publish(new FixAsset(world.id(), "t7"))
                .publish(new FixAsset(world.id(), "t8"));

        deleteTechnician(world, "t5");

//        //TODO: Fallar misión a propósito
//        world.players().find("t6").failMission("FixOneAsset");
//        world.players().find("t9").failMission("FixOneAsset");
//
//        //TODO: Completar misión a propósito
//        world.players().find("t7").completeMission("FixOneAsset");
//        world.players().find("t10").completeMission("FixOneAsset");
//
//        //TODO: Cancelar misión a propósito
//        world.players().find("t8").cancelMission("FixOneAsset");
//        world.players().find("t11").cancelMission("FixOneAsset");

        world.finishCurrentMatch();

        engine.graph().save();

        System.out.println();
    }

    private static Cinesa createWorld() {

        Cinesa world = new Cinesa("world");

        FixOneAsset mission = new FixOneAsset();
        world.missions().add(mission);
        MonthEmployee achievement = new MonthEmployee();
        world.achievements().add(achievement);

        initTechnician(world, "t1", Arrays.asList("a1", "a2"));
        initTechnician(world, "t2", Arrays.asList("a3", "a4"));
        initTechnician(world, "t3", Arrays.asList("a5", "a6"));
        initTechnician(world, "t4", Arrays.asList("a7", "a8"));
        initTechnician(world, "t5", Arrays.asList("a9", "a10"));
        initTechnician(world, "t6", Arrays.asList("a11", "a12"));
        initTechnician(world, "t7", Arrays.asList("a13", "a14"));
        initTechnician(world, "t8", Arrays.asList("a15", "a16"));
        initTechnician(world, "t9", Arrays.asList("a17", "a18"));
        initTechnician(world, "t10", Arrays.asList("a19", "a20"));
        initTechnician(world, "t11", Arrays.asList("a21", "a22"));

        GamificationGraph.get().worlds().add(world);

        return world;
    }

    private static void initTechnician(Cinesa world, String technicianId, List<String> assetIds) {
        Technician technician = new Technician(world.id(), technicianId);

        for (String assetId : assetIds) {
            Asset asset = new Asset(world.id(), assetId);
            world.items().add(asset);
            technician.inventory().add(asset);
        }

        world.players().add(technician);
    }

    private static void deleteTechnician(Cinesa world, String technicianId) {
        world.players().destroy(world.players().find(technicianId));
    }

    private static Log.Logger getLogger() {
        return new Log.Logger() {
            @Override
            public void debug(String message) {
                Logger.debug(message);
            }

            @Override
            public void info(String message) {
                Logger.info(message);
            }

            @Override
            public void warn(String message) {
                Logger.warn(message);
            }

            @Override
            public void error(String message) {
                Logger.error(message);
            }

            @Override
            public void error(String message, Throwable e) {
                Logger.error(message, e);
            }
        };
    }
}
