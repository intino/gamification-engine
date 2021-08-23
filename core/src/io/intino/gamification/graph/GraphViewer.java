package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.World;

import java.util.List;

public class GraphViewer {

    private GamificationCore core;

    public GraphViewer(GamificationCore core) {
        this.core = core;
    }

    public List<World> worlds() {
        return core.graph().worlds().list();
    }

    public World world(String worldId) {
        return core.graph().worlds().find(worldId);
    }
}
