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

public class TestUtils {

    public static final String Stashes = "Control";
    public static final String[] StartUpStashes = {Stashes, "Employees", "Assets", "Alerts"};
    public static final String[] Arguments = new String[]{"home=temp", "datahub_url=failover:(tcp://localhost:63000)", "datahub_user=cinepolis", "datahub_password=cinepolis", "datahub_clientId=cinepolis", "datahub_outbox_directory=temp/terminals/example/cinepolis", "datalake_path=temp/datalake"};

    public static ControlBox createBox() {
        ControlBox box = new ControlBox(Arguments);

        Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
        box.put(graph);

        Map<String, String> engineConfig = box.configuration().args();
        engineConfig.put("gamification_datamart_path", "./temp/datamarts/gamiex");

        GamificationEngine engine = new GamificationEngine(engineConfig);
        box.put(engine);

        engine.launch(box::start);
        engine.waitFor();

        return box;
    }

    public static void newWorkingDay(ControlBox box) {
        World world = box.engine().datamart().world(GamificationConfig.WorldId);
        if(world != null) {
            Match match = world.match();
            if(match != null) {
                box.engine().terminal().feed(endMatch(match.id()));
            }
        }

        box.engine().terminal().feed(beginMatch());

        CreateAchievement la = (CreateAchievement) new CreateAchievement()
                .world(GamificationConfig.WorldId)
                .type(AchievementType.Local)
                .description("Arregla 3 proyectores")
                .eventInvolved(EventType.NewStateMission)
                .maxCount(3)
                .id(UUID.randomUUID().toString())
                .ts(TimeUtils.currentInstant());

        Achievement localAchievement = box.engine().terminal().feed(la);
        localAchievement.<NewStateMission>progressIf((e, p) -> {
            if (e.player().equals(p.id()) && e.state().equals(MissionState.Completed)) {
                return CheckResult.Progress;
            } else {
                return CheckResult.Skip;
            }
        });
    }

    public static DeregisterAsset deleteAsset(String id) {
        return new DeregisterAsset()
                .ts(TimeUtils.currentInstant())
                .id(id);
    }

    public static RegisterAsset newAsset(String id, String name, String area) {
        return new RegisterAsset()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .name(name)
                .area(area);
    }

    public static HireEmployee newEmployee(String id, String name, int age, String phone, String area) {
        return new HireEmployee()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .name(name)
                .age(age)
                .phone(phone)
                .area(area);
    }

    public static DismissEmployee deleteEmployee(String id) {
        return new DismissEmployee()
                .ts(TimeUtils.currentInstant())
                .id(id);
    }

    public static AssetAlert generateAlert(String id, String asset, AssetAlert.Importance importance, int limitHours, String description) {
        return new AssetAlert()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .asset(asset)
                .importance(importance)
                .limitHours(limitHours)
                .description(description);
    }

    public static FixedAsset completeAlert(String alert, String asset, String employee) {
        return new FixedAsset()
                .ts(TimeUtils.currentInstant())
                .alert(alert)
                .asset(asset)
                .employee(employee);
    }

    public static BeginMatch beginMatch() {
        return (BeginMatch) new BeginMatch()
                .world(GamificationConfig.WorldId)
                .expiration(TimeUtils.nextInstant(TimeUtils.currentInstant(), TimeUtils.Scale.Minute))
                .reboot(true)
                .id(UUID.randomUUID().toString())
                .ts(TimeUtils.currentInstant());
    }

    public static EndMatch endMatch(String matchId) {
        return (EndMatch) new EndMatch()
                .world(GamificationConfig.WorldId)
                .id(matchId)
                .ts(TimeUtils.currentInstant());
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
