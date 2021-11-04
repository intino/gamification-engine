package org.example.cinepolis.control.gamification;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.graph.model.old.Item;
import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.Player;
import io.intino.gamification.graph.model.Competition;
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

import static io.intino.gamification.graph.model.old.Actor.InventoryPolicy.Drop;

public class Adapter {

    //TODO: El adapter debe crear eventos de gamificación a partir de los eventos de cinépolis

    private final GamificationEngine engine;
    private final ControlGraph graph;

    private Competition competition;

    public Adapter(ControlBox box) {
        this.engine = box.engine();
        this.graph = box.graph();
        initialize();
    }

    public void initialize() {

        Competition competition = engine.graph().competitions().find(GamificationConfig.WorldId);

        if(competition == null) {
            this.competition = engine.graph().createCompetition(new Competition(GamificationConfig.WorldId));
            this.competition.missions().addAll(initMissions());
        } else {
            this.competition = competition;
        }
    }

    private List<Mission> initMissions() {
        return Arrays.asList(
                new AtencionTicketsOTRSMission(),
                new ParosFuncionMission(),
                new EntradaTimeTrackerMission(),
                new ReportesServicioMission(),
                new EnvioPlannerMission()
        );
    }

    public Competition world() {
        return competition;
    }

    public void adapt(AssetAlert event) {
        org.example.cinepolis.control.graph.Asset asset = graph.asset(event.asset());
        if(asset == null) return;
        if(competition.currentSeason() == null) return;

        List<String> employees = graph.employeesByArea(asset.area()).stream()
                .map(org.example.cinepolis.control.graph.Employee::id)
                .collect(Collectors.toList());

        for(String employee : employees) {
            //world.players().find(employee).assignMission(new FixOneAssetAssignment(world().id(), world().currentMatch().id(), employee));
        }
    }

    public void adapt(DeregisterAsset event) {
        Item item = competition.items().find(event.id());
        competition.items().destroy(item);
    }

    public void adapt(DismissEmployee event) {
        Player player = competition.players().find(event.id());
        competition.players().destroy(player);
    }

    public void adapt(FixedAsset event) {
        boolean anyAsset = graph.assetList().stream().anyMatch(as -> as.id().equals(event.asset()) && as.alerts().stream().anyMatch(al -> al.id().equals(event.alert())));
        if(!anyAsset) return;
        FixAsset fixAssetEvent = new FixAsset(GamificationConfig.WorldId, event.employee());
        engine.eventPublisher().publish(fixAssetEvent);
    }

    public void adapt(HireEmployee event) {

        Employee employee = new Employee(competition.id(), event.id());
        employee.inventory().policy(Drop);

        for (org.example.cinepolis.control.graph.Asset asset : graph.assetsByArea(event.area())) {
            Asset item = new Asset(competition.id(), asset.id());
            competition.items().add(item);
            employee.inventory().add(item);
        }

        competition.players().add(employee);
    }

    public void adapt(RegisterAsset event) {
        Asset asset = new Asset(GamificationConfig.WorldId, event.id());
        competition.items().add(asset);

        org.example.cinepolis.control.graph.Employee employee = graph.employeeByArea(event.area());
        if(employee == null) return;
        Player player = competition.players().find(employee.id());
        player.inventory().add(asset);
    }
}
