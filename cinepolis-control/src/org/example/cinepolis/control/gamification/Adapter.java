package org.example.cinepolis.control.gamification;

import io.intino.gamification.core.box.checkers.CheckResult;
import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.action.Heal;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.mission.MissionDifficulty;
import io.intino.gamification.core.box.events.mission.MissionType;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.utils.TimeUtils;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.Mission;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.graph.Asset;
import org.example.cinepolis.control.graph.Employee;
import org.example.cinepolis.datahub.events.cinepolis.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Adapter {

    private final ControlBox box;

    public Adapter(ControlBox box) {
        this.box = box;
    }

    public void initialize() {
        CreateWorld cw = (CreateWorld) new CreateWorld()
                .id(GamificationConfig.WorldId)
                .ts(TimeUtils.currentInstant());
        CreateAchievement ga = (CreateAchievement) new CreateAchievement()
                .world(GamificationConfig.WorldId)
                .type(AchievementType.Global)
                .description("Empieza 2 partidas")
                .event(EventType.BeginMatch)
                .maxCount(2)
                .id("achievement1")
                .ts(TimeUtils.currentInstant());

        box.engine().terminal().feed(cw);
        box.engine().terminal().feed(ga);

        Achievement globalAchievement = box.engine().datamart().globalAchievement(GamificationConfig.WorldId, "achievement1");
        globalAchievement.<BeginMatch>progressIf((event, player) -> CheckResult.Progress);
    }

    public void adapt(AssetAlert event) {
        Asset asset = box.graph().asset(event.asset());
        if(asset == null) return;

        List<String> employees = box.graph().employeesByArea(asset.area()).stream()
                .map(Employee::id)
                .collect(Collectors.toList());

        CreateMission nm = (CreateMission) new CreateMission()
                .world(GamificationConfig.WorldId)
                .type(typeOf(event.importance()))
                .difficulty(MissionDifficulty.Medium)
                .description(event.description())
                .players(employees)
                .event(EventType.Heal)
                .maxCount(1)
                .id(event.id())
                .ts(TimeUtils.currentInstant());

        box.engine().terminal().feed(nm);

        Mission mission = box.engine().datamart().mission(GamificationConfig.WorldId, event.id());
        if(mission != null) {
            mission.<Heal>progressIf((a, p) -> {
                if(a.type().equals("FixAsset") &&
                        a.entitySrc().equals(p.id()) &&
                        p.inventory().stream().anyMatch(i -> i.id().equals(a.entityDest()))) {
                    return CheckResult.Progress;
                }
                return CheckResult.Skip;
            });
        }
    }

    public void adapt(DeregisterAsset event) {
        DestroyItem di = (DestroyItem) new DestroyItem()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(TimeUtils.currentInstant());
        box.engine().terminal().feed(di);
    }

    public void adapt(DismissEmployee event) {
        DestroyPlayer dp = (DestroyPlayer) new DestroyPlayer()
                .destroyStrategy(DestroyStrategy.Nothing)
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(TimeUtils.currentInstant());
        box.engine().terminal().feed(dp);
    }

    public void adapt(FixedAsset event) {
        Heal heal = (Heal) new Heal()
                .healedHealth(100.0)
                .entitySrc(event.employee())
                .entityDest(event.asset())
                .world(GamificationConfig.WorldId)
                .type("FixAsset")
                .id(UUID.randomUUID().toString())
                .ts(Instant.now());
        box.engine().terminal().feed(heal);
    }

    public void adapt(HireEmployee event) {
        CreatePlayer cp = (CreatePlayer) new CreatePlayer()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(TimeUtils.currentInstant());
        box.engine().terminal().feed(cp);

        List<Asset> assets = box.graph().assetsByArea(event.area());
        assets.forEach(a -> {
            PickUpItem pui = (PickUpItem) new PickUpItem()
                    .world(GamificationConfig.WorldId)
                    .player(event.id())
                    .id(a.id())
                    .ts(TimeUtils.currentInstant());
            box.engine().terminal().feed(pui);
        });
    }

    public void adapt(RegisterAsset event) {
        CreateItem ci = (CreateItem) new CreateItem()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(TimeUtils.currentInstant());
        box.engine().terminal().feed(ci);

        Employee employee = box.graph().employeeByArea(event.area());
        if(employee == null) return;
        PickUpItem pui = (PickUpItem) new PickUpItem()
                .world(GamificationConfig.WorldId)
                .player(employee.id())
                .id(event.id())
                .ts(TimeUtils.currentInstant());
        box.engine().terminal().feed(pui);
    }

    private MissionType typeOf(AssetAlert.Importance importance) {
        switch (importance) {
            case Low:
                return MissionType.Secondary;
            case Medium:
                return MissionType.Primary;
            case Important:
                return MissionType.Special;
            default:
                return null;
        }
    }
}
