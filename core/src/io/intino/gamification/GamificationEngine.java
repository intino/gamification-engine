package io.intino.gamification;

import io.intino.gamification.core.Configuration;
import io.intino.gamification.api.EventPublisher;
import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.core.GamificationParameters;
import io.intino.gamification.api.GraphViewer;

import java.util.Map;

public class GamificationEngine {

    private final GamificationParameters gamificationParameters;

    private GamificationCore core;

    private Configuration configuration;
    private GraphViewer graphViewer;
    private EventPublisher eventPublisher;

    public GamificationEngine(Map<String, String> params) {
        this.gamificationParameters = new GamificationParameters(params);
    }

    public void launch() {
        this.core = new GamificationCore(gamificationParameters);
        this.core.start();

        this.configuration = core.configuration();
        this.graphViewer = core.graphViewer();
        this.eventPublisher = core.eventPublisher();
    }

    public Configuration configuration() {
        return this.configuration;
    }

    public GraphViewer graphViewer() {
        return this.graphViewer;
    }

    public EventPublisher eventPublisher() {
        return this.eventPublisher;
    }
}
