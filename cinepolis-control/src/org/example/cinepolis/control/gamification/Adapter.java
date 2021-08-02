package org.example.cinepolis.control.gamification;

import old.core.box.checkers.CheckResult;
import old.core.box.events.EventType;
import old.core.box.events.action.Heal;
import old.core.box.events.entity.*;
import old.core.box.events.mission.CreateMission;
import old.core.box.events.world.CreateWorld;
import old.core.box.listeners.EventProcessListener;
import old.core.box.mappers.PlayerLevelMapper;
import old.core.graph.Player;
import old.core.model.Mission;
import old.core.model.attributes.DestroyStrategy;
import old.core.model.attributes.MissionDifficulty;
import old.core.model.attributes.MissionType;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.graph.Asset;
import org.example.cinepolis.control.graph.Employee;
import org.example.cinepolis.datahub.events.cinepolis.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static old.core.box.utils.TimeUtils.*;

public class Adapter {

    private final ControlBox box;

    public Adapter(ControlBox box) {
        this.box = box;
    }

    public void initialize() {
        box.engine().configuration().gameLoopConfigurator.schedule( 16, Scale.Millis);
        box.engine().configuration().missionScoreMapper.set((player, mission, state) -> Math.round(state.multiplier() * mission.difficulty().multiplier() * mission.type().multiplier()));
        box.engine().configuration().playerLevelMapper.set(new PlayerLevelMapper() {
            @Override
            public int level(Player player, int score) {
                return (int) (score /10.0);
            }
        });

        CreateWorld cw = (CreateWorld) new CreateWorld()
                .id(GamificationConfig.WorldId)
                .ts(currentInstant());
        /*CreateAchievement ga = (CreateAchievement) new CreateAchievement()
                .world(GamificationConfig.WorldId)
                .type(AchievementType.Global)
                .description("Empieza 2 partidas")
                .eventInvolved(EventType.BeginMatch)
                .maxCount(2)
                .id("achievement1")
                .ts(currentInstant());*/

        box.engine().terminal().feed(cw);

        /*EventProcessListener<Achievement> eventProcessListener = achievement -> achievement.<PickUpItem>progressIf((event, player) -> CheckResult.Progress);
        box.engine().terminal().feed(ga, eventProcessListener);*/
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
                .eventInvolved(EventType.Heal)
                .maxCount(1)
                .expiration(nextInstant(event.ts(), Scale.Hour, event.limitHours()))
                .id(UUID.randomUUID().toString())
                .ts(currentInstant());

        EventProcessListener<Mission> eventProcessListener = mission -> {
            mission.<Heal>progressIf((a, p) -> {
                if(a.type().equals("FixAsset") &&
                        a.entitySrc().equals(p.id()) &&
                        p.inventory().stream().anyMatch(i -> i.id().equals(a.entityDest()))) {
                    return CheckResult.Progress;
                }
                return CheckResult.Skip;
            });
        };
        box.engine().terminal().feed(nm, eventProcessListener);
    }

    public void adapt(DeregisterAsset event) {
        DestroyItem di = (DestroyItem) new DestroyItem()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(currentInstant());
        box.engine().terminal().feed(di);
    }

    public void adapt(DismissEmployee event) {
        DestroyPlayer dp = (DestroyPlayer) new DestroyPlayer()
                .destroyStrategy(DestroyStrategy.Nothing)
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(currentInstant());
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
                .ts(currentInstant());
        box.engine().terminal().feed(heal);
    }

    public void adapt(HireEmployee event) {
        CreatePlayer cp = (CreatePlayer) new CreatePlayer()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(currentInstant());
        box.engine().terminal().feed(cp);

        List<Asset> assets = box.graph().assetsByArea(event.area());
        assets.forEach(a -> {
            PickUpItem pui = (PickUpItem) new PickUpItem()
                    .world(GamificationConfig.WorldId)
                    .player(event.id())
                    .id(a.id())
                    .ts(currentInstant());
            box.engine().terminal().feed(pui);
        });
    }

    public void adapt(RegisterAsset event) {
        CreateItem ci = (CreateItem) new CreateItem()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(currentInstant());
        box.engine().terminal().feed(ci);

        Employee employee = box.graph().employeeByArea(event.area());
        if(employee == null) return;
        PickUpItem pui = (PickUpItem) new PickUpItem()
                .world(GamificationConfig.WorldId)
                .player(employee.id())
                .id(event.id())
                .ts(currentInstant());
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
