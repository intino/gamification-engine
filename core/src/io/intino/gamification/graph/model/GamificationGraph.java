package io.intino.gamification.graph.model;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.util.data.NodeCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamificationGraph {

    private static GamificationGraph instance;

    public static GamificationGraph get() {
        return instance;
    }

    private final GamificationCore core;
    private final DeferredNodeCollection<World> worlds;

    public GamificationGraph(GamificationCore core) {
        if(core == null) throw new IllegalArgumentException("GamificationCore cannot be null");
        GamificationGraph.instance = this;
        this.core = core;
        this.worlds = new DeferredNodeCollection<>();
    }

    public NodeCollection<World> worlds() {
        return worlds;
    }

    public void update() {
        worlds.update();
        for (World world : worlds) {
            world.update();
        }
    }
}
