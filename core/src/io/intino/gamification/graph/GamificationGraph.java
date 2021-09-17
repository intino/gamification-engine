package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.NodeCollection;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.Log;

public class GamificationGraph {

    private static volatile GamificationGraph instance;

    public static GamificationGraph get() {
        return instance;
    }

    private final GamificationCore core;
    private final NodeCollection<World> worlds;

    public GamificationGraph(GamificationCore core) {
        if(core == null) {
            IllegalArgumentException e = new IllegalArgumentException("GamificationCore cannot be null");
            Log.error(e);
            throw e;
        }
        this.core = core;
        this.worlds = new NodeCollection<>();
        GamificationGraph.instance = this;
    }

    public World createWorld(World world) {
        worlds.add(world);
        return world;
    }

    public NodeCollection<World> worlds() {
        return worlds;
    }

    public void save() {
        core.graphSerializer().save();
    }
}
