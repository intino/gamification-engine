package io.intino.gamification;

import io.intino.gamification.api.Configuration;
import io.intino.gamification.core.launcher.ParameterProcessor;
import io.intino.gamification.events.EventManager;
import io.intino.gamification.model.Datamart;
import io.intino.gamification.core.GamificationCore;

import java.util.Map;

public class GamificationEngine {

    private final ParameterProcessor parameterProcessor;

    private GamificationCore core;

    private Configuration configuration;
    private Datamart datamart;
    private EventManager eventManager;

    public GamificationEngine(Map<String, String> params) {
        this.parameterProcessor = new ParameterProcessor(params);
    }

    public void launch() {
        this.core = new GamificationCore(parameterProcessor);
        this.core.start();

        this.configuration = core.configuration();
        this.datamart = core.datamart();
        this.eventManager = core.eventManager();
    }

    public Configuration configuration() {
        return this.configuration;
    }

    public Datamart datamart() {
        return this.datamart;
    }

    public EventManager eventManager() {
        return this.eventManager;
    }
}
