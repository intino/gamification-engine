package io.intino.gamification;

import io.intino.alexandria.core.BoxConfiguration;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.launcher.Launcher;
import io.intino.gamification.core.box.terminal.Terminal;

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

    public Terminal terminal() {
        return box.terminal();
    }
}
