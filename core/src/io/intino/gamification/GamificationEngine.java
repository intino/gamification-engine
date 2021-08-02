package io.intino.gamification;

import io.intino.gamification.api.Configuration;
import io.intino.gamification.core.launcher.ParameterProcessor;
import io.intino.gamification.model.Datamart;
import io.intino.gamification.core.Core;

import java.util.Map;

public class GamificationEngine {

    private final ParameterProcessor parameterProcessor;

    private Core core;
    private Configuration configuration;
    private Datamart datamart;

    public GamificationEngine(Map<String, String> params) {
        this.parameterProcessor = new ParameterProcessor(params);
    }

    public void launch() {
        this.core = new Core(parameterProcessor);
        this.core.start();

        this.configuration = core.configuration();
        this.datamart = core.datamart();
    }

    public Configuration configuration() {
        return this.configuration;
    }

    public Datamart datamart() {
        return this.datamart;
    }
}
