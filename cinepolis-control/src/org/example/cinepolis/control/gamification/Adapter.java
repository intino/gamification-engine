package org.example.cinepolis.control.gamification;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.graph.model.Item;
import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.Player;
import io.intino.gamification.graph.model.World;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.events.FixAsset;
import org.example.cinepolis.control.gamification.model.Asset;
import org.example.cinepolis.control.gamification.model.Employee;
import org.example.cinepolis.control.gamification.model.mission.*;
import org.example.cinepolis.control.graph.ControlGraph;
import org.example.cinepolis.datahub.events.cinepolis.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.intino.gamification.graph.model.Actor.InventoryPolicy.Drop;

public class Adapter {

    private final GamificationEngine engine;
    private final ControlGraph graph;

    private World world;

    public Adapter(ControlBox box) {
        this.engine = box.engine();
        this.graph = box.graph();
        initialize();
    }

    public void initialize() {

        World world = engine.graph().worlds().find(GamificationConfig.WorldId);

        if(world == null) {
            this.world = engine.graph().createWorld(new World(GamificationConfig.WorldId));
            this.world.missions().addAll(initMissions());
        } else {
            this.world = world;
        }
    }

    private List<Mission> initMissions() {
        return Arrays.asList(
                new AtencionTicketsOTRSMission(),
                new ParosFuncion(),
                new EntradaTimeTracker(),
                new ReportesServicio(),
                new EnvioPlanner()
        );
    }

    public World world() {
        return world;
    }

    public void adapt(AssetAlert event) {
        org.example.cinepolis.control.graph.Asset asset = graph.asset(event.asset());
        if(asset == null) return;
        if(world.currentMatch() == null) return;

        List<String> employees = graph.employeesByArea(asset.area()).stream()
                .map(org.example.cinepolis.control.graph.Employee::id)
                .collect(Collectors.toList());

        for(String employee : employees) {
            //world.players().find(employee).assignMission(new FixOneAssetAssignment(world().id(), world().currentMatch().id(), employee));
        }
    }

    public void adapt(DeregisterAsset event) {
        Item item = world.items().find(event.id());
        world.items().destroy(item);
    }

    public void adapt(DismissEmployee event) {
        Player player = world.players().find(event.id());
        world.players().destroy(player);
    }

    public void adapt(FixedAsset event) {
        boolean anyAsset = graph.assetList().stream().anyMatch(as -> as.id().equals(event.asset()) && as.alerts().stream().anyMatch(al -> al.id().equals(event.alert())));
        if(!anyAsset) return;
        FixAsset fixAssetEvent = new FixAsset(GamificationConfig.WorldId, event.employee());
        engine.eventPublisher().publish(fixAssetEvent);
    }

    public void adapt(HireEmployee event) {

        Employee employee = new Employee(world.id(), event.id());
        employee.inventory().policy(Drop);

        for (org.example.cinepolis.control.graph.Asset asset : graph.assetsByArea(event.area())) {
            Asset item = new Asset(world.id(), asset.id());
            world.items().add(item);
            employee.inventory().add(item);
        }

        world.players().add(employee);
    }

    public void adapt(RegisterAsset event) {
        Asset asset = new Asset(GamificationConfig.WorldId, event.id());
        world.items().add(asset);

        org.example.cinepolis.control.graph.Employee employee = graph.employeeByArea(event.area());
        if(employee == null) return;
        Player player = world.players().find(employee.id());
        player.inventory().add(asset);
    }
}
