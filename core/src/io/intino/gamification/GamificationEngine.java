package io.intino.gamification;

import io.intino.gamification.events.EventManager;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.time.TimeUtils;

public class GamificationEngine {

    private final GamificationConfig configuration;
    private GamificationGraph graph;
    private EventManager eventManager;

    public GamificationEngine(GamificationConfig configuration) {
        this.configuration = configuration;
    }

    public void launch() {
        TimeUtils.zoneOffset(configuration.zoneOffset());

        this.graph = new GamificationGraph();
        this.eventManager = new EventManager();

        this.graph = new GamificationGraph();
        this.eventManager = new EventManager();
    }

    public GamificationConfig configuration() {
        return this.configuration;
    }

    public GamificationGraph graph() {
        return this.graph;
    }

    public EventManager eventManager() {
        return this.eventManager;
    }
}
