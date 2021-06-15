package org.example.vaccine.control.box.adapter;

import io.intino.gamification.core.box.events.entity.CreatePlayer;
import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.box.gamification.GameWorld;
import org.example.vaccine.datahub.events.vaccines.CreateHospital;

import java.util.List;

public class CreateHospitalAdapter extends Adapter<CreateHospital, CreatePlayer> {

    public CreateHospitalAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected List<CreatePlayer> doAdapt(CreateHospital event) {
        CreatePlayer player = new CreatePlayer();
        player.id(event.name());
        player.world(GameWorld.getID());
        player.health(100);
        player.enabled(true);
        return List.of(player);
    }
}
