package io.intino.gamification.core;

import io.intino.gamification.api.Configuration;
import io.intino.gamification.core.checker.Checker;
import io.intino.gamification.core.checker.Checkers;
import io.intino.gamification.core.launcher.ParameterProcessor;
import io.intino.gamification.core.mounter.Mounters;
import io.intino.gamification.events.EventRegister;
import io.intino.gamification.events.Terminal;
import io.intino.gamification.model.Datamart;
import io.intino.gamification.model.Graph;
import io.intino.gamification.utils.Util;
import io.intino.gamification.utils.Utils;

public class Core {

    private final Configuration configuration;
    private Utils utils;

    private Graph graph;
    private Datamart datamart;

    private Mounters mounters;
    private EventRegister eventRegister;
    private Terminal terminal;
    private Checkers checkers;

    private GameLoop gameLoop;

    public Core(ParameterProcessor parameterProcessor) {
        this.configuration = new Configuration(parameterProcessor);
    }

    public void start() {
        this.utils = new Utils(this);

        this.graph = new Graph(this);
        this.datamart = new Datamart(this);

        this.mounters = new Mounters(this);
        this.eventRegister = new EventRegister(this);
        this.terminal = new Terminal(this);
        this.checkers = new Checkers(this);

        this.gameLoop = new GameLoop(this);
    }

    public Configuration configuration() {
        return this.configuration;
    }

    public Util util(Class<? extends Util> clazz) {
        return this.utils.util(clazz);
    }

    public Graph graph() {
        return graph;
    }

    public Datamart datamart() {
        return this.datamart;
    }

    public Mounters mounters() {
        return this.mounters;
    }

    public EventRegister eventRegister() {
        return eventRegister;
    }

    public Terminal terminal() {
        return terminal;
    }

    public Checker checker(Class<? extends Checker> clazz) {
        return this.checkers.checker(clazz);
    }

    public GameLoop gameLoop() {
        return gameLoop;
    }
}