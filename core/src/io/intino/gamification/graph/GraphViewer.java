package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.World;

public class GraphViewer {

    private GamificationCore core;

    public GraphViewer(GamificationCore core) {
        this.core = core;
    }

    public World world(String worldId) {
        return core.graph().worlds().find(worldId);
    }
}
