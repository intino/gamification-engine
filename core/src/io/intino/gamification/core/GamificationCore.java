package io.intino.gamification.core;

import io.intino.gamification.api.Configuration;
import io.intino.gamification.core.checker.Checker;
import io.intino.gamification.core.checker.Checkers;
import io.intino.gamification.core.launcher.ParameterProcessor;
import io.intino.gamification.events.EventManager;
import io.intino.gamification.model.Datamart;
import io.intino.gamification.model.GamificationGraph;
import io.intino.gamification.utils.Util;
import io.intino.gamification.utils.Utils;

public class GamificationCore {

    private final Configuration configuration;
    private GamificationGraph graph;
    private Datamart datamart;
    private EventManager eventManager;
    private Checkers checkers;
    private GameLoop gameLoop;
    private Utils utils;

    public GamificationCore(ParameterProcessor parameterProcessor) {
        this.configuration = new Configuration(parameterProcessor);
    }

    public void start() {
        initSubSystems();
        datamart.load();
    }

    private void initSubSystems() {
        this.utils = new Utils(this);
        this.graph = new GamificationGraph(this);
        this.datamart = new Datamart(this);
        this.eventManager = new EventManager(this);
        this.checkers = new Checkers(this);
        this.gameLoop = new GameLoop(this);
    }

    public Configuration configuration() {
        return this.configuration;
    }

    public <T extends Util> T util(Class<T> clazz) {
        return this.utils.util(clazz);
    }

    public GamificationGraph graph() {
        return graph;
    }

    public Datamart datamart() {
        return this.datamart;
    }

    public EventManager eventManager() {
        return eventManager;
    }

    public <T extends Checker> T checker(Class<T> clazz) {
        return this.checkers.checker(clazz);
    }

    public GameLoop gameLoop() {
        return gameLoop;
    }
}