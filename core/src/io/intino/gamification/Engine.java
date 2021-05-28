package io.intino.gamification;

import io.intino.alexandria.core.BoxConfiguration;
import io.intino.alexandria.logger.Logger;
import io.intino.gamification.core.box.CoreBox;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.apache.log4j.Level;

import java.io.File;
import java.util.Map;

public class Engine extends Async {

    private static final String Gamification = "Gamification";
    private static final String[] StartUpStashes = {Gamification, "Player", "Npc", "Mission", "Achievement"};

    private final String[] args;

    public Engine(BoxConfiguration configuration) {
        this.args = argsFrom(configuration.args());
    }

    public void run() {
        Logger.init(Level.ERROR);
        CoreBox box = new CoreBox(args);
        Model model = new Model(box.configuration());
        model.start();
        Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
        box.put(graph);
        box.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            box.stop();
            stop();
        }));
        onStart.run();
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
        return new String[] {
                "home=" + args.get("home"),
                "datahub_url=" + "failover:(tcp://localhost:64000)",
                "datahub_user=" + "gamification",
                "datahub_password=" + "gamification",
                "datahub_clientId=" + "gamification",
                "datahub_outbox_directory=" + getOutboxDirectoryFrom(args.get("datahub_outbox_directory")),
                "datalake_path=" + args.get("datalake_path")
        };
    }

    private String getOutboxDirectoryFrom(String datahub_outbox_directory) {
        int index = datahub_outbox_directory.lastIndexOf("/");
        return datahub_outbox_directory.substring(0, index + 1) + "gamification";
    }
}
