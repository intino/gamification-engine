import io.intino.gamification.Engine;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.graph.ControlGraph;

import java.io.File;

public class Test {

    private static final String Stashes = "Control";
    private static final String[] StartUpStashes = {Stashes, "Employees", "Assets", "Alerts"};

    public static void main(String[] args) {

        args = new String[] {"home=temp", "datahub_url=failover:(tcp://localhost:63000)", "datahub_user=cinepolis", "datahub_password=cinepolis", "datahub_clientId=cinepolis", "datahub_outbox_directory=temp/terminals/example/cinepolis", "datalake_path=temp/datalake"};

        ControlBox box = new ControlBox(args);
        Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
        box.put(graph);

        Engine engine = new Engine(box.configuration());
        box.put(engine);

        engine.launch(() -> {
            box.start();
            box.graph().clear();
            Gamification.run(box);
            Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
        });
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
