package org.example.vaccine.mock;

import io.intino.gamification.GamificationEngine;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.graph.ControlGraph;

import java.io.File;

public class ControlBoxMock {

    private static final String Stashes = "Control";
    private static final String[] StartUpStashes = {Stashes};

    public static ControlBox getControlBox() {
        ControlBox box = new ControlBox(new String[] {
                "home=temp",
                "datahub_url=failover:(tcp://localhost:63000)",
                "datahub_user=example",
                "datahub_password=example",
                "datahub_clientId=example",
                "datahub_outbox_directory=temp/terminals/example",
                "datalake_path=temp/datalake"
        });

        Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
        box.put(new ControlGraph(graph));

        GamificationEngine engine = new GamificationEngine(box.configuration());
        box.put(engine);

        engine.launch(() -> {
            box.start();
            Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
        });

        engine.waitFor();

        return box;
    }

    private static FileSystemStore store(File datamartFolder) {
        return new FileSystemStore(datamartFolder) {

            @Override
            public Stash stashFrom(String path) {
                Stash stash = super.stashFrom(path);
                if (stash != null && stash.language == null) stash.language = Stashes;
                return stash;
            }

            @Override
            public void writeStash(Stash stash, String path) {
                stash.language = stash.language == null || stash.language.isEmpty() ? Stashes : stash.language;
                super.writeStash(stash, path);
            }
        };
    }
}
