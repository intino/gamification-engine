package cinepolis;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.core.box.checkers.CheckResult;
import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.utils.TimeUtils;
import io.intino.gamification.core.model.Achievement;
import io.intino.gamification.core.model.Match;
import io.intino.gamification.core.model.World;
import io.intino.gamification.core.model.attributes.AchievementType;
import io.intino.gamification.core.model.attributes.MissionState;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.GamificationConfig;
import org.example.cinepolis.datahub.events.cinepolis.*;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import static cinepolis.TestUtils.*;

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

    public static class Gamification {

        public static void run(ControlBox box) {
            box.terminal().publish(newEmployee("empleado1", "Pepe", 29, "622785202", "area1"));
            box.terminal().publish(newEmployee("empleado2", "Jose", 30, "622785203", "area2"));
            box.terminal().publish(newEmployee("empleado3", "Rafa", 42, "622785204", "area3"));
            box.terminal().publish(newEmployee("empleado4", "Pedro", 18, "622785205", "area4"));
            box.terminal().publish(newEmployee("empleado5", "Juan", 35, "622785206", "area5"));

            box.terminal().publish(newAsset("asset1", "Proyector 1", "area1"));
            box.terminal().publish(newAsset("asset2", "Proyector 2", "area1"));
            box.terminal().publish(newAsset("asset3", "Proyector 3", "area1"));
            box.terminal().publish(newAsset("asset4", "Proyector 4", "area2"));
            box.terminal().publish(newAsset("asset5", "Proyector 5", "area2"));
            box.terminal().publish(newAsset("asset6", "Proyector 6", "area2"));
            box.terminal().publish(newAsset("asset7", "Proyector 7", "area3"));
            box.terminal().publish(newAsset("asset8", "Proyector 8", "area3"));
            box.terminal().publish(newAsset("asset9", "Proyector 9", "area3"));
            box.terminal().publish(newAsset("asset10", "Proyector 10", "area4"));
            box.terminal().publish(newAsset("asset11", "Proyector 11", "area4"));
            box.terminal().publish(newAsset("asset12", "Proyector 12", "area4"));
            box.terminal().publish(newAsset("asset13", "Proyector 13", "area5"));
            box.terminal().publish(newAsset("asset14", "Proyector 14", "area5"));
            box.terminal().publish(newAsset("asset15", "Proyector 15", "area5"));

            newWorkingDay(box);

            box.terminal().publish(deleteEmployee("empleado1"));
            box.terminal().publish(deleteAsset("asset4"));

            box.terminal().publish(generateAlert("alert1", "asset5", AssetAlert.Importance.Important, 1, "Arregla el asset 5"));
            box.terminal().publish(generateAlert("alert2", "asset7", AssetAlert.Importance.Low, 2, "Arregla el asset 7"));
            box.terminal().publish(generateAlert("alert3", "asset10", AssetAlert.Importance.Medium, 3, "Arregla el asset 10"));
            box.terminal().publish(generateAlert("alert4", "asset15", AssetAlert.Importance.Medium, 3, "Arregla el asset 15"));

            box.terminal().publish(completeAlert("alert1", "asset5", "empleado2"));
            box.terminal().publish(completeAlert("alert2", "asset10", "empleado3"));
            box.terminal().publish(completeAlert("alert3", "asset10", "empleado3"));
            box.terminal().publish(completeAlert("alert4", "asset15", "empleado5"));

            System.out.println();
        }
    }
}
