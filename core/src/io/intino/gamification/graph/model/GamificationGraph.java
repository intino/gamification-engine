package io.intino.gamification.graph.model;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.util.data.NodeCollection;

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
        //TODO REGISTRAR ERROR
        if(core == null) throw new IllegalArgumentException("GamificationCore cannot be null");
        this.core = core;
        this.worlds = new DeferredNodeCollection<>();
        this.saveRequested = new AtomicBoolean(true);
        GamificationGraph.instance = this;
    }

    public NodeCollection<World> worlds() {
        return worlds;
    }

    public void update() {
        if(worlds.sealContents()) save();
        for (World world : worlds) {
            world.update();
        }
    }

    public void save() {
        shouldSave(true);
    }

    public void shouldSave(boolean shouldSave) {
        saveRequested.set(shouldSave);
    }

    public boolean shouldSave() {
        return saveRequested.get();
    }
}
