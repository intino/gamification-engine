package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.NodeCollection;
import io.intino.gamification.graph.model.Competition;
import io.intino.gamification.util.Log;

public class GamificationGraph {

    private static volatile GamificationGraph instance;

    public static GamificationGraph get() {
        return instance;
    }

    private final GamificationCore core;
    private final NodeCollection<Competition> competitions;

    public GamificationGraph(GamificationCore core) {
        if(core == null) {
            IllegalArgumentException e = new IllegalArgumentException("GamificationCore cannot be null");
            Log.error(e);
            throw e;
        }
        this.core = core;
        this.competitions = new NodeCollection<>();
        GamificationGraph.instance = this;
    }

    public NodeCollection<Competition> competitions() {
        return competitions;
    }

    public void save() {
        core.graphSerializer().save();
    }
}
