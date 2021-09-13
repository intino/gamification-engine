package org.example.cinepolis.control.gamification;

import io.intino.gamification.graph.model.*;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.events.FixAsset;
import org.example.cinepolis.control.gamification.model.*;
import org.example.cinepolis.control.graph.Employee;
import org.example.cinepolis.datahub.events.cinepolis.*;

import java.util.List;
import java.util.stream.Collectors;

import static io.intino.gamification.graph.model.Actor.InventoryPolicy.Drop;

public class Adapter {

    private final ControlBox box;

    private World world;

    public Adapter(ControlBox box) {
        this.box = box;
    }

    public void initialize() {

        World world = box.engine().graphViewer().world(GamificationConfig.WorldId);
        if(world == null) {
            this.world = new Cinema(GamificationConfig.WorldId);

            FixOneAsset mission = new FixOneAsset();
            this.world.missions().add(mission);
            BeginTwoMatches achievement = new BeginTwoMatches();
            this.world.achievements().add(achievement);

            this.world.currentMatch(new Workday(GamificationConfig.WorldId, "match"));
        } else {
            this.world = world;
        }
    }

    public void adapt(AssetAlert event) {
        org.example.cinepolis.control.graph.Asset asset = box.graph().asset(event.asset());
        if(asset == null) return;

        List<String> employees = box.graph().employeesByArea(asset.area()).stream()
                .map(Employee::id)
                .collect(Collectors.toList());

        for(String employee : employees) {
            world.players().find(employee).assignMission("FixOneAsset");
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
        boolean anyAsset = box.graph().assetList().stream().anyMatch(as -> as.id().equals(event.asset()) && as.alerts().stream().anyMatch(al -> al.id().equals(event.alert())));
        if(!anyAsset) return;
        FixAsset fixAssetEvent = new FixAsset(GamificationConfig.WorldId, event.employee());
        box.engine().eventPublisher().publish(fixAssetEvent);
    }

    public void adapt(HireEmployee event) {

        Technician technician = new Technician(world.id(), event.id());
        technician.inventory().policy(Drop);

        for (org.example.cinepolis.control.graph.Asset asset : box.graph().assetsByArea(event.area())) {
            Asset item = new Asset(world.id(), asset.id());
            world.items().add(item);
            technician.inventory().add(item);
        }

        world.players().add(technician);
    }

    public void adapt(RegisterAsset event) {
        Asset asset = new Asset(GamificationConfig.WorldId, event.id());
        world.items().add(asset);

        Employee employee = box.graph().employeeByArea(event.area());
        if(employee == null) return;
        Player player = world.players().find(employee.id());
        player.inventory().add(asset);
    }
}
