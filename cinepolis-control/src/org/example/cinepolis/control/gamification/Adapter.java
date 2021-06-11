package org.example.cinepolis.control.gamification;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.mission.MissionDifficulty;
import io.intino.gamification.core.box.events.mission.MissionType;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.logic.CheckerHandler;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;
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
        CreateWorld cw = (CreateWorld) new CreateWorld().id(GamificationConfig.WorldId).ts(Instant.now());
        BeginMatch bm = (BeginMatch) new BeginMatch().world(GamificationConfig.WorldId).id(UUID.randomUUID().toString()).ts(Instant.now());

        box.engine().terminal().feed(cw);
        box.engine().terminal().feed(bm);
    }

    public void adapt(AssetAlert event) {
        Asset asset = box.graph().asset(event.asset());
        if(asset == null) return;

        List<String> employees = box.graph().employeesByArea(asset.area()).stream()
                .map(Employee::id)
                .collect(Collectors.toList());

        NewMission nm = (NewMission) new NewMission()
                .world(GamificationConfig.WorldId)
                .type(typeOf(event.importance()))
                .difficulty(MissionDifficulty.Medium)
                .description(event.description())
                .players(employees)
                .event(EventType.Action)
                .maxCount(1)
                .id(event.id())
                .ts(Instant.now());

        box.engine().terminal().feed(nm);

        Mission mission = box.engine().datamart().mission(event.id());
        if(mission != null) {
            mission.progressIf(new CheckerHandler.Checker<Action>() {
                @Override
                public boolean check(Action a, Player p) {
                    return a.type().equals("FixAsset") && a.entitySrc().equals(p.id()) && p.inventory().stream().anyMatch(i -> i.id().equals(a.entityDest()));
                }
            });
            //mission.progressIf((CheckerHandler.Checker<Action>) (a, p) -> a.type().equals("FixAsset") && a.entitySrc().equals(p.id()));
        }
    }

    public void adapt(DeregisterAsset event) {
        DestroyEntity de = (DestroyEntity) new DestroyEntity()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(Instant.now());
        box.engine().terminal().feed(de);
    }

    public void adapt(DismissEmployee event) {
        DestroyEntity de = (DestroyEntity) new DestroyEntity()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(Instant.now());
        box.engine().terminal().feed(de);
    }

    public void adapt(FixedAsset event) {
        Action a = Action.heal(GamificationConfig.WorldId, event.employee(), event.asset(), "FixAsset", 100);
        box.engine().terminal().feed(a);
    }

    public void adapt(HireEmployee event) {
        CreatePlayer cp = (CreatePlayer) new CreatePlayer()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(Instant.now());
        box.engine().terminal().feed(cp);

        List<Asset> assets = box.graph().assetsByArea(event.area());
        assets.forEach(a -> {
            PickUpItem pui = (PickUpItem) new PickUpItem()
                    .world(GamificationConfig.WorldId)
                    .player(event.id())
                    .id(a.id())
                    .ts(Instant.now());
            box.engine().terminal().feed(pui);
        });
    }

    public void adapt(RegisterAsset event) {
        CreateItem ci = (CreateItem) new CreateItem()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(Instant.now());
        box.engine().terminal().feed(ci);

        Employee employee = box.graph().employeeByArea(event.area());
        if(employee == null) return;
        PickUpItem pui = (PickUpItem) new PickUpItem()
                .world(GamificationConfig.WorldId)
                .player(employee.id())
                .id(event.id())
                .ts(Instant.now());
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
