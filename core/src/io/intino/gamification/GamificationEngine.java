package io.intino.gamification;

import io.intino.gamification.core.Configuration;
import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.core.GamificationParameters;
import io.intino.gamification.events.EventManager;
import io.intino.gamification.graph.GamificationGraph;

import java.util.Map;

public class GamificationEngine {

    private final GamificationParameters gamificationParameters;

    private GamificationCore core;

    private Configuration configuration;
    private GamificationGraph graph;
    private EventManager eventManager;

    public GamificationEngine(Map<String, String> params) {
        this.gamificationParameters = new GamificationParameters(params);
    }

    public void launch() {
        this.core = new GamificationCore(gamificationParameters);
        this.core.start();

        this.configuration = core.configuration();
        this.graph = core.graph();
        this.eventManager = core.eventManager();
    }

    public Configuration configuration() {
        return this.configuration;
    }

    public GamificationGraph graph() {
        return this.graph;
    }

    public EventManager eventManager() {
        return this.eventManager;
    }
}
