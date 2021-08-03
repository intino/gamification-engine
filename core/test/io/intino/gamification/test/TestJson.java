package io.intino.gamification.test;

import io.intino.gamification.graph.model.Actor;
import io.intino.gamification.graph.model.Player;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.utils.Json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class TestJson {

    public static void main(String[] args) throws IOException {
        writeWorld();
        readWorld();
    }

    private static void readWorld() throws IOException {
        World world = Json.fromJson(
                Files.readString(new File("C:\\Users\\naits\\Desktop\\MonentiaDev\\gamification-engine\\core\\test\\io\\intino\\gamification\\test\\world#TheWorld.json").toPath()),
                World.class);

        System.out.println(world);
    }

    private static void writeWorld() throws IOException {
        long start = System.currentTimeMillis();

        World world = new World("TheWorld");

        for(int i = 0;i < 100000;i++) {
            Player p = new Player(world.id(), "Player_"+i);
            p.health((float) (i * Math.random()));
            world.players().add(p);
        }

        for(int i = 0;i < 100000;i++) {
            Actor p = new Actor(world.id(), "Npc_"+i);
            p.health((float) (i * Math.random()));
            world.npcs().add(p);
        }

        world.update();

        Files.writeString(
                new File("C:\\Users\\naits\\Desktop\\MonentiaDev\\gamification-engine\\core\\test\\io\\intino\\gamification\\test\\world#TheWorld.json").toPath(),
                Json.toJsonPretty(world));

        long end = System.currentTimeMillis();

        System.out.println((end - start) / 1000.0 + " seconds");
    }

    private static Map<String, String> getArguments() {
        return new HashMap<>() {{
            put("", ""); // ...
        }};
    }
}
