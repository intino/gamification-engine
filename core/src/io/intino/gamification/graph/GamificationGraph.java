package io.intino.gamification.graph;

import io.intino.gamification.graph.model.NodeCollection;
import io.intino.gamification.graph.model.Competition;
import io.intino.gamification.util.Log;

public class GamificationGraph {

    private static volatile GamificationGraph instance;

    public static GamificationGraph get() {
        return instance;
    }

    private final NodeCollection<Competition> competitions;

    public GamificationGraph() {
        this.competitions = new NodeCollection<>();
        competitions.init("", Competition.class);
        GamificationGraph.instance = this;
    }

    public NodeCollection<Competition> competitions() {
        return competitions;
    }

    public void save() {
        // TODO
    }
}
