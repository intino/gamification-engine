package org.example.cinepolis.control.gamification;

import io.intino.gamification.events.MissionEventListener;
import io.intino.gamification.graph.model.*;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.events.FixAsset;
import org.example.cinepolis.control.graph.Asset;
import org.example.cinepolis.control.graph.Employee;
import org.example.cinepolis.datahub.events.cinepolis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Adapter {

    public class Tecnico extends Player {

        public Tecnico(String world, String id) {
            super(world, id);
        }
    }

    private final ControlBox box;

    private World world;

    public Adapter(ControlBox box) {
        this.box = box;
    }

    public void initialize() {
        this.world = World.create(GamificationConfig.WorldId);

        //world.players().create("id", Tecnico::new);

        this.world = new World(GamificationConfig.WorldId);
        Match match = new Match(GamificationConfig.WorldId, "match", missionDefinitions(), new Match.ExpirationConfig(true, 3600));
        Achievement achievement = new Achievement("achievement1", "Empieza 2 partidas");
        //Cómo me suscribo a la creación de un match?
    }

    private List<Mission> missionDefinitions() {
        List<Mission> missions = new ArrayList<>();
        Mission mission = new Mission("mission1", "Arregla 1 proyector");
        mission.subscribe(FixAsset.class, new MissionEventListener<FixAsset>() {
            @Override
            public void invoke(FixAsset event, Mission mission, Player player, MissionAssignment missionAssignment) {
                //Como se progresa?
                missionAssignment.progress().set(1);
            }
        });
        missions.add(mission);
        return missions;
    }

    public void adapt(AssetAlert event) {
        Asset asset = box.graph().asset(event.asset());
        if(asset == null) return;

        List<String> employees = box.graph().employeesByArea(asset.area()).stream()
                .map(Employee::id)
                .collect(Collectors.toList());

        employees.forEach(e -> new MissionAssignment("mission1", e));
    }

    public void adapt(DeregisterAsset event) {
        //Item item = world.item(event.id());
        Item item = world.items().find(event.id());
        world.items().destroy(item);
    }

    public void adapt(DismissEmployee event) {
        //Player player = world.player(event.id());
        Player player = world.players().find(event.id());
        world.players().destroy(player);
    }

    public void adapt(FixedAsset event) {
        FixAsset fixAssetEvent = new FixAsset(event.employee());
        box.engine().eventManager().publish(fixAssetEvent);
    }

    public void adapt(HireEmployee event) {
        Player player = new Player(GamificationConfig.WorldId, event.id());
        world.players().add(player);
        box.graph().assetsByArea(event.area()).forEach(a -> player.inventory().add(a.id()));
    }

    public void adapt(RegisterAsset event) {
        Item item = new Item(world.id(), event.id());
        world.items().add(item);

        Employee employee = box.graph().employeeByArea(event.area());
        if(employee == null) return;
        Player player = world.players().find(employee.id());
        player.inventoryPolicy(Actor.InventoryPolicy.Drop);
        //player.inventory().policy(Actor.InventoryPolicy.Drop);
        player.inventory().add(item);
    }
}
