package io.intino.gamification;

import io.intino.alexandria.core.BoxConfiguration;
import io.intino.gamification.api.EngineDatamart;
import io.intino.gamification.api.EngineTerminal;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.launcher.Launcher;

import java.util.Map;

public class Engine {

    private final Launcher launcher;
    private CoreBox box;

    public Engine(BoxConfiguration configuration) {
        this.launcher = new Launcher(configuration);
    }

    public Engine(Map<String, String> arguments) {
        this.launcher = new Launcher(arguments);
    }

    public void launch() {
        launcher.onStart(() -> box = launcher.box());
        launcher.start();
    }

    public void launch(Runnable onStartCallback) {
        launcher.onStart(() -> {
            box = launcher.box();
            onStartCallback.run();
        });
        launcher.start();
    }

    public EngineTerminal terminal() {
        return box.engineTerminal();
    }

    public EngineDatamart datamart() {
        return box.engineDatamart();
    }
}
