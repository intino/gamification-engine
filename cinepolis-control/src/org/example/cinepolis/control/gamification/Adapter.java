package org.example.cinepolis.control.gamification;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.events.MissionProgressEventManager;
import io.intino.gamification.graph.model.Competition;
import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.Player;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.model.Employee;
import org.example.cinepolis.control.gamification.model.mission.*;
import org.example.cinepolis.control.graph.ControlGraph;
import org.example.cinepolis.datahub.events.cinepolis.AssetAlert;
import org.example.cinepolis.datahub.events.cinepolis.DismissEmployee;
import org.example.cinepolis.datahub.events.cinepolis.FixedAsset;
import org.example.cinepolis.datahub.events.cinepolis.HireEmployee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Adapter {

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

    public void adapt(DismissEmployee event) {
        Player player = competition.players().find(event.id());
        competition.players().destroy(player);
    }

    public void adapt(FixedAsset event) {
        boolean anyAsset = graph.assetList().stream().anyMatch(as -> as.id().equals(event.asset()) && as.alerts().stream().anyMatch(al -> al.id().equals(event.alert())));
        if(!anyAsset) return;
        MissionProgressEventManager.get().call(competition, "FixAsset", event.employee());
    }

    public void adapt(HireEmployee event) {
        Employee employee = new Employee(event.id());
        competition.players().add(employee);
    }
}
