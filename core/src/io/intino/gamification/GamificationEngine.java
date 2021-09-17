package io.intino.gamification;

import io.intino.gamification.core.Configuration;
import io.intino.gamification.events.EventPublisher;
import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.core.GamificationParameters;
import io.intino.gamification.graph.GamificationGraph;

import java.util.Map;

public class GamificationEngine {

    private final GamificationParameters gamificationParameters;

    private GamificationCore core;

    private Configuration configuration;
    private GamificationGraph graph;
    private EventPublisher eventPublisher;

    public GamificationEngine(Map<String, String> params) {
        this.gamificationParameters = new GamificationParameters(params);
    }

    public void launch() {
        this.core = new GamificationCore(gamificationParameters);
        this.core.start();

        this.configuration = core.configuration();
        this.graph = core.graph();
        this.eventPublisher = core.eventPublisher();
    }

    public Configuration configuration() {
        return this.configuration;
    }

    public GamificationGraph graph() {
        return this.graph;
    }

    public EventPublisher eventPublisher() {
        return this.eventPublisher;
    }
}
