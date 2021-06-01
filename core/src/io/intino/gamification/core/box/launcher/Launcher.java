package io.intino.gamification.core.box.launcher;

import io.intino.alexandria.core.BoxConfiguration;
import io.intino.gamification.core.box.CoreBox;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;

import java.io.File;
import java.util.Map;

public class Launcher extends Async {

    private static final String Gamification = "Gamification";
    private static final String[] StartUpStashes = {Gamification, "Entity", "Mission", "Achievement", "Match"};

    private final String[] args;
    private CoreBox box;

    public Launcher(BoxConfiguration configuration) {
        this.args = argsFrom(configuration.args());
    }

    @Override
    protected void run() {
        this.box = new CoreBox(args);
        Graph graph = new Graph(store(this.box.datamart().root())).loadStashes(false, StartUpStashes);
        this.box.put(graph);
        this.box.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.box.stop();
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
                "datalake_path=" + args.get("datalake_path")
        };
    }

    public CoreBox box() {
        return box;
    }
}
