package io.intino.gamification;

import io.intino.alexandria.core.BoxConfiguration;
import io.intino.gamification.api.EngineDatamart;
import io.intino.gamification.api.EngineTerminal;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.launcher.Launcher;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.Entity;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.Mission;

import java.util.List;

public class Engine {

    private final Launcher launcher;
    private CoreBox box;

    public Engine(BoxConfiguration configuration) {
        this.launcher = new Launcher(configuration);
    }

    public void launch() {
        launcher.start();
        box = launcher.box();
    }

    public void launch(Runnable onStartCallback) {
        launcher.onStart(onStartCallback);
        launch();
    }

    public EngineTerminal terminal() {
        return box.engineTerminal();
    }

    public EngineDatamart datamart() {
        return box.engineDatamart();
    }
}
