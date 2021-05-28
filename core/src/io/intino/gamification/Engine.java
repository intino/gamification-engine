package io.intino.gamification;

import io.intino.alexandria.core.BoxConfiguration;
import io.intino.alexandria.logger4j.Logger;
import io.intino.gamification.core.box.CoreBox;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.apache.log4j.Level;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Engine implements Runnable {

    private final String[] args;

    private static final String Gamification = "Gamification";
    private static final String[] StartUpStashes = {Gamification, "Player", "Npc", "Mission", "Achievement"};

    public Engine(BoxConfiguration configuration) {
        args = argsFrom(configuration.args());
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {

        Logger.init(Level.ERROR);
        CoreBox box = new CoreBox(args);
        Model model = new Model(box.configuration());
        model.start();
        Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
        box.put(graph);
        box.start();
        Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
    }

    private static FileSystemStore store(File datamartFolder) {
        return new FileSystemStore(datamartFolder) {

            @Override
            public Stash stashFrom(String path) {
                Stash stash = super.stashFrom(path);
                if (stash != null && stash.language == null) stash.language = Gamification;
                return stash;
            }

            @Override
            public void writeStash(Stash stash, String path) {
                stash.language = stash.language == null || stash.language.isEmpty() ? Gamification : stash.language;
                super.writeStash(stash, path);
            }
        };
    }

    private String[] argsFrom(Map<String, String> args) {

        Map<String, String> engineArgs = new HashMap<>();
        engineArgs.put("home", args.get("home"));
        engineArgs.put("datahub_url", "failover:(tcp://localhost:64000)");
        engineArgs.put("datahub_user", "gamification");
        engineArgs.put("datahub_password", "gamification");
        engineArgs.put("datahub_clientId", "gamification");
        engineArgs.put("datahub_outbox_directory", getOutboxDirectoryFrom(args.get("datahub_outbox_directory")));
        engineArgs.put("datalake_path", args.get("datalake_path"));

        return engineArgs.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).toArray(String[]::new);
    }

    private String getOutboxDirectoryFrom(String datahub_outbox_directory) {
        int index = datahub_outbox_directory.lastIndexOf("/");
        return datahub_outbox_directory.substring(0, index + 1) + "gamification";
    }
}
