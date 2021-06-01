package io.intino.gamification;

import io.intino.alexandria.core.BoxConfiguration;
import io.intino.gamification.core.box.launcher.Launcher;

public class Engine {

    private final Launcher launcher;

    public Engine(BoxConfiguration configuration) {
        this.launcher = new Launcher(configuration);
    }

    public void launch() {
        launcher.start();
    }

    public void launch(Runnable onStartCallback) {
        launcher.onStart(onStartCallback);
        launch();
    }
}
