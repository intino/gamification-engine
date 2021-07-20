package cinepolis;

import io.intino.gamification.GamificationEngine;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.example.cinepolis.control.box.ControlBox;

import java.io.File;
import java.util.Map;

public class Test {

    private static final String Stashes = "Control";
    private static final String[] StartUpStashes = {Stashes, "Employees", "Assets", "Alerts"};

    public static void main(String[] args) {
        run();
    }

    public static ControlBox run() {

        String[] args = new String[] {"home=temp", "datahub_url=failover:(tcp://localhost:63000)", "datahub_user=cinepolis", "datahub_password=cinepolis", "datahub_clientId=cinepolis", "datahub_outbox_directory=temp/terminals/example/cinepolis", "datalake_path=temp/datalake"};

        ControlBox box = new ControlBox(args);
        Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
        box.put(graph);

        Map<String, String> engineConfig = box.configuration().args();
        engineConfig.put("gamification_datamart_path", "./temp/datamarts/gamiex");

        GamificationEngine engine = new GamificationEngine(engineConfig);
        box.put(engine);

        engine.launch(() -> {
            box.start();
            Gamification.run(box);
            Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
        });

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