import io.intino.gamification.api.model.*;
import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.mission.MissionState;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.utils.TimeUtils;
import io.intino.gamification.core.box.checkers.CheckResult;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.GamificationConfig;
import org.example.cinepolis.datahub.events.cinepolis.*;

import java.util.UUID;

public class Gamification {

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

    private static void newWorkingDay(ControlBox box) {
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

    private static DeregisterAsset deleteAsset(String id) {
        return new DeregisterAsset()
                .ts(TimeUtils.currentInstant())
                .id(id);
    }

    private static RegisterAsset newAsset(String id, String name, String area) {
        return new RegisterAsset()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .name(name)
                .area(area);
    }

    private static HireEmployee newEmployee(String id, String name, int age, String phone, String area) {
        return new HireEmployee()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .name(name)
                .age(age)
                .phone(phone)
                .area(area);
    }

    private static DismissEmployee deleteEmployee(String id) {
        return new DismissEmployee()
                .ts(TimeUtils.currentInstant())
                .id(id);
    }

    private static AssetAlert generateAlert(String id, String asset, AssetAlert.Importance importance, int limitHours, String description) {
        return new AssetAlert()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .asset(asset)
                .importance(importance)
                .limitHours(limitHours)
                .description(description);
    }

    private static FixedAsset completeAlert(String alert, String asset, String employee) {
        return new FixedAsset()
                .ts(TimeUtils.currentInstant())
                .alert(alert)
                .asset(asset)
                .employee(employee);
    }

    private static BeginMatch beginMatch() {
        return (BeginMatch) new BeginMatch()
                .world(GamificationConfig.WorldId)
                .reboot(false)
                .ts(TimeUtils.currentInstant());
    }

    private static EndMatch endMatch(String matchId) {
        return (EndMatch) new EndMatch()
                .world(GamificationConfig.WorldId)
                .id(matchId)
                .ts(TimeUtils.currentInstant());
    }
}