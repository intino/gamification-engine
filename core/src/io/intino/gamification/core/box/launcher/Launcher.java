package io.intino.gamification.core.box.launcher;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.graph.stash.Stash;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;

import java.io.File;
import java.util.Map;

public class Launcher extends Async {

    private static final String Gamification = Stash.main();
    private static final String[] StartUpStashes = Stash.stashes();

    private final String[] args;
    private CoreBox box;

    public Launcher(Map<String, String> arguments) {
        this.args = argsFrom(arguments);
    }

    @Override
    protected void run() {
        try {
            this.box = new CoreBox(args);
            Graph graph = new Graph(store(this.box.datamart())).loadStashes(false, StartUpStashes);
            this.box.put(graph);
            this.box.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                this.box.stop();
                stop();
            }));
            onStart.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static FileSystemStore store(File datamartFolder) {
        return new FileSystemStore(datamartFolder) {

            @Override
            public io.intino.magritte.io.Stash stashFrom(String path) {
                io.intino.magritte.io.Stash stash = super.stashFrom(path);
                if (stash != null && stash.language == null) stash.language = Gamification;
                return stash;
            }

            @Override
            public void writeStash(io.intino.magritte.io.Stash stash, String path) {
                stash.language = stash.language == null || stash.language.isEmpty() ? Gamification : stash.language;
                super.writeStash(stash, path);
            }
        };
    }

    private String[] argsFrom(Map<String, String> args) {
        return new String[] {
                "home=" + args.get("home"),
                "datalake_path=" + args.get("datalake_path"),
                "datamart_path=" + args.get("gamification_datamart_path")
        };
    }

    public CoreBox box() {
        return box;
    }
}
