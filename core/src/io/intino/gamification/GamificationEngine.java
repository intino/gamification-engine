package io.intino.gamification;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.time.TimeUtils;

public class GamificationEngine {

    private final GamificationConfig configuration;
    private GamificationGraph graph;

    public GamificationEngine(GamificationConfig configuration) {
        this.configuration = configuration;
    }

    public void launch() {
        TimeUtils.zoneOffset(configuration.zoneOffset());
        this.graph = new GamificationGraph();
    }

    public GamificationConfig configuration() {
        return this.configuration;
    }

    public GamificationGraph graph() {
        return this.graph;
    }
}
