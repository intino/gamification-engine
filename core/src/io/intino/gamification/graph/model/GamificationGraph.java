package io.intino.gamification.graph.model;

import io.intino.gamification.core.GamificationCore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamificationGraph {

    private static GamificationGraph instance;

    public static GamificationGraph get() {
        return instance;
    }

    private final GamificationCore core;
    private final Worlds worlds;

    public GamificationGraph(GamificationCore core) {
        if(core == null) throw new IllegalArgumentException("GamificationCore cannot be null");
        GamificationGraph.instance = this;
        this.core = core;
        this.worlds = new Worlds();
    }

    public Worlds worlds() {
        return worlds;
    }

    public void update() {
        worlds.update();
    }

    public static final class Worlds {

        private final List<World> worlds;

        public Worlds() {
            this.worlds = new ArrayList<>();
        }

        public void add(World world) {
            if(exists(world.id())) throw new IllegalArgumentException("World " + world.id() + " already exists");
        }

        public void destroy(String id) {
            World world = get(id);
            if(world == null) return;
            worlds.remove(world);
            // TODO: delete from disk etc
        }

        public World get(String id) {
            return worlds.stream().filter(world -> world.id().equals(id)).findFirst().orElse(null);
        }

        public boolean exists(String id) {
            return worlds.stream().anyMatch(world -> world.id().equals(id));
        }

        public List<World> getAll() {
            return Collections.unmodifiableList(worlds);
        }

        private void update() {
            for(World world : worlds) {
                world.update();
            }
        }
    }
}
