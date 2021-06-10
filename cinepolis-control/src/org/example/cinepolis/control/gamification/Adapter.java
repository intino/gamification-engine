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
        Asset asset = box.graph().asset(event.id());
        if(asset == null) return;

        String id = UUID.randomUUID().toString();
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
                .id(id)
                .ts(Instant.now());

        box.engine().terminal().feed(nm);

        Mission mission = box.engine().datamart().mission(id);
        if(mission != null) {
            mission.progressIf((CheckerHandler.Checker<Action>) (a, p) -> a.type().equals("FixAsset") && a.entitySrc().equals(p.id()));
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
    }

    public void adapt(RegisterAsset event) {
        CreateItem ci = (CreateItem) new CreateItem()
                .world(GamificationConfig.WorldId)
                .id(event.id())
                .ts(Instant.now());
        PickUpItem pui = (PickUpItem) new PickUpItem()
                .world(GamificationConfig.WorldId)
                .player(box.graph().employeeByArea(event.area()).id())
                .id(event.id())
                .ts(Instant.now());

        box.engine().terminal().feed(ci);
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
