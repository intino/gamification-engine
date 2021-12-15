package io.intino.gamification;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.time.TimeUtils;

import static java.util.Objects.requireNonNull;

public class GamificationEngine {

    private final GamificationConfig configuration;
    private GamificationGraph graph;

    public GamificationEngine(GamificationConfig configuration) {
        this.configuration = configuration;
    }

    public void launch() {
        TimeUtils.zoneOffset(configuration.zoneOffset());
    }

    public GamificationConfig configuration() {
        return this.configuration;
    }

    public GamificationGraph graph() {
        return this.graph;
    }

    public GamificationEngine setGraph(GamificationGraph graph) {
        this.graph = requireNonNull(graph);
        return this;
    }
}
