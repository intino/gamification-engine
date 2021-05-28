package io.intino.gamification;

import io.intino.alexandria.core.Box;
import io.intino.alexandria.core.BoxConfiguration;
import io.intino.alexandria.logger.Logger;
import io.intino.datahub.box.DataHubBox;
import io.intino.datahub.box.DataHubConfiguration;
import io.intino.datahub.graph.NessGraph;
import io.intino.magritte.framework.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Model {

    private static final String[] NessStashes = {"Solution"};

    private final String[] args;
    private final ExecutorService thread;
    private final AtomicBoolean running;
    private Runnable onStart;

    public Model(BoxConfiguration configuration) {
        this.args = argsFrom(configuration.args());
        this.thread = Executors.newSingleThreadExecutor(Model::thread);
        this.running = new AtomicBoolean();
        this.onStart = () -> {};
    }

    public Model onStart(Runnable onStartCallback) {
        this.onStart = onStartCallback != null ? onStartCallback : () -> {};
        return this;
    }

    public boolean start() {
        if(!running.compareAndSet(false, true)) {
            Logger.warn("Model " + hashCode() + " is already running");
            return false;
        }
        thread.submit(this::run);
        return true;
    }

    private void run() {
        DataHubConfiguration configuration = new DataHubConfiguration(args);
        NessGraph nessGraph = new Graph().loadStashes(NessStashes).as(NessGraph.class);
        loadUsers(configuration.home(), nessGraph);
        Box box = new DataHubBox(args).put(nessGraph.core$()).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            box.stop();
            stop();
        }));
        onStart.run();
    }

    public boolean running() {
        return running.get();
    }

    public boolean stop() {
        return stop(0, TimeUnit.SECONDS);
    }

    public boolean stop(long timeout, TimeUnit timeUnit) {
        if(!running.compareAndSet(true, false)) return false;
        try {
            thread.shutdown();
            thread.awaitTermination(timeout, timeUnit);
        } catch (InterruptedException e) {
            Logger.error(e);
        }
        return true;
    }

    private static void loadUsers(File workspace, NessGraph nessGraph) {
        try {
            File file = new File(workspace, "users.bin");
            if (!file.exists()) return;
            nessGraph.broker().clear().user(u -> true);
            String[] users = new String(Files.readAllBytes(file.toPath())).split("\n");
            for (String user : users) nessGraph.broker().create().user(user.split("::")[0], user.split("::")[1]);
        } catch (IOException e) {
            Logger.error("Failed to load users", e);
        }
    }

    private String[] argsFrom(Map<String, String> args) {
        return new String[] {
                "home" + args.get("home"),
                "backup_directory" + getBackupFrom(args.get("home")),
                "datalake_directory" + args.get("datalake_path"),
                "broker_directory" + getBrokerFrom(args.get("home")),
                "broker_port" + "64000",
                "broker_secondary_port" + "1884",
                "ui_port" + "9030"
        };
    }

    private String getBackupFrom(String home) {
        return home + "/backup";
    }

    private String getBrokerFrom(String home) {
        return home + "/datahub/broker";
    }

    private static Thread thread(Runnable runnable) {
        return new Thread(runnable, "Model-Thread");
    }
}
