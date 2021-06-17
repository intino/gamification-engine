package io.intino.gamification;

import io.intino.alexandria.core.BoxConfiguration;
import io.intino.alexandria.logger.Logger;
import io.intino.gamification.api.EngineConfiguration;
import io.intino.gamification.api.EngineDatamart;
import io.intino.gamification.api.EngineTerminal;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.launcher.Launcher;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Engine {

    private final Launcher launcher;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private CoreBox box;

    public Engine(BoxConfiguration configuration) {
        this(configuration.args());
    }

    public Engine(Map<String, String> arguments) {
        this.launcher = new Launcher(arguments);
    }

    public void launch() {
        launch(() -> {});
    }

    public void launch(Runnable onStartCallback) {
        launcher.onStart(() -> {
            box = launcher.box();
            onStartCallback.run();
            countDownLatch.countDown();
        });
        launcher.start();
    }

    public void waitFor() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Logger.error(e);
        }
    }

    public void waitFor(long timeout, TimeUnit timeUnit) {
        try {
            countDownLatch.await(timeout, timeUnit);
        } catch (InterruptedException e) {
            Logger.error(e);
        }
    }

    public EngineConfiguration configuration() {
        return box.engineConfig();
    }

    public EngineTerminal terminal() {
        return box.engineTerminal();
    }

    public EngineDatamart datamart() {
        return box.engineDatamart();
    }
}
