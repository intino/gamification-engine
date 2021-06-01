package io.intino.gamification;

import io.intino.alexandria.core.BoxConfiguration;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.launcher.Launcher;
import io.intino.gamification.core.box.terminal.Terminal;
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

    public Terminal terminal() {
        return box.terminal();
    }

    public List<Entity> entities() {
        return box.graph().entityList();
    }

    public List<Mission> missions() {
        return box.graph().missionList();
    }

    public List<Achievement> achievements() {
        return box.graph().achievementList();
    }

    public List<Match> matches() {
        return box.graph().matchList();
    }
}
