package org.example.control.box.gamification;

import io.intino.gamification.core.box.events.entity.CreatePlayer;
import io.intino.gamification.core.box.events.world.CreateWorld;
import org.example.control.box.ControlBox;
import org.example.control.graph.Hospital;
import org.example.datahub.events.example.CreateHospital;

public class CreateHospitalAdapter {

    private final ControlBox box;

    public CreateHospitalAdapter(ControlBox box) {
        this.box = box;
    }

    public void adapt(CreateHospital event) {
        if(box.graph().contains(event.name(), Hospital.class)) return;

        if(box.engine().datamart().world(event.location()) == null) {
            CreateWorld world = new CreateWorld();
            world.id(event.location());
            box.engine().terminal().feed(world);
        }

        CreatePlayer player = new CreatePlayer();
        player.id(event.name());

        box.engine().terminal().feed(player);
    }
}
