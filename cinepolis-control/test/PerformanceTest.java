import io.intino.gamification.GamificationEngine;
import io.intino.gamification.graph.model.*;
import org.example.cinepolis.control.gamification.model.Asset;
import util.events.FixAsset;
import util.model.Cinesa;
import util.model.Technician;

import java.util.HashMap;
import java.util.Map;

import static io.intino.gamification.util.time.Scale.Day;
import static io.intino.gamification.util.time.TimeUtils.*;

public class PerformanceTest {

    private final static int worlds = 1;
    private final static int achievements = 1;
    private final static int missions = 1;
    private final static int players = 100;
    private final static int itemsPerPlayer = 5;
    private final static int npcs = 0;
    private final static int matches = 1000;

    public static void main(String[] args) throws InterruptedException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("gamification_time_zone", "Atlantic/Canary");
        parameters.put("gamification_path", "./temp/datamarts/cinepolis-gamification");

        GamificationEngine engine = new GamificationEngine(parameters);
        engine.launch();

        /* ----------------------------------------------------------------- */

        for (int i = 0; i < worlds; i++) {
            createWorld(engine, "world-" + i);
        }

        System.out.println();
    }

    private static void createWorld(GamificationEngine engine, String id) throws InterruptedException {

        Cinesa world = new Cinesa(id);

        for (int i = 0; i < achievements; i++) {
            world.achievements().add(new Achievement("achievement-" + i, "description", 1));
        }

        for (int i = 0; i < missions; i++) {
            world.missions().add(new Mission("achievement-" + i, "description", 1) {
                @Override
                protected void setProgressCallbacks() {

                }
            });
        }

        for (int i = 0; i < players; i++) {
            createPlayer(world, "player-" + i);
        }

        for (int i = 0; i < npcs; i++) {
            world.npcs().add(new Actor(world.id(), "npc-" + i));
        }

        for (int i = 0; i < matches; i++) {
            Thread.sleep(100);
            world.startNewMatch(new Match(world.id(), "match-" + i));
        }

        Thread.sleep(3000);

        for (Player player : world.players()) {
            for (Mission mission : world.missions()) {
                //TODO
                //player.assignMission(mission.id(), truncateTo(nextInstant(currentInstant(), Day), Day));
            }
        }

        for (int i=0; i<players*2; i++) {
            String playerId = world.players().list().get(Math.min(players - 1, (int) (Math.random() * players))).id();
            engine.eventPublisher().publish(new FixAsset(world.id(), playerId));
        }
    }

    private static void createPlayer(World world, String id) {
        Technician player = new Technician(world.id(), id);

        for (int i = 0; i < itemsPerPlayer; i++) {
            Asset asset = new Asset(world.id(), id + "-item-" + i);
            player.inventory().add(asset);
            world.items().add(asset);
        }

        world.players().add(player);
    }
}
