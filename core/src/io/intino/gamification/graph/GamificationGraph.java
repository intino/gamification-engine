package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.DeferredNodeCollection;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.graph.structure.NodeCollection;
import io.intino.gamification.util.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

public class GamificationGraph {

    private static volatile GamificationGraph instance;

    public static GamificationGraph get() {
        return instance;
    }

    private final GamificationCore core;
    private final DeferredNodeCollection<World> worlds;
    private final AtomicBoolean saveRequested;

    public GamificationGraph(GamificationCore core) {
        if(core == null) {
            IllegalArgumentException e = new IllegalArgumentException("GamificationCore cannot be null");
            Logger.error(e);
            throw e;
        }
        this.core = core;
        this.worlds = new DeferredNodeCollection<>();
        this.saveRequested = new AtomicBoolean(true);
        GamificationGraph.instance = this;
    }

    public NodeCollection<World> worlds() {
        return worlds;
    }

    public void update() {
        worlds.sealContents();

        for (World world : worlds) {
            if(world != null && world.isAvailable()) world.update();
        }
    }

    public void shouldSave(boolean shouldSave) {
        saveRequested.set(shouldSave);
    }

    public boolean shouldSave() {
        return saveRequested.get();
    }
}
