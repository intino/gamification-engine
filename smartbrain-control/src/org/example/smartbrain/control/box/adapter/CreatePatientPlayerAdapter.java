package org.example.smartbrain.control.box.adapter;

import old.core.box.events.GamificationEvent;
import old.core.box.events.entity.CreatePlayer;
import org.example.smartbrain.control.box.ControlBox;
import org.example.smartbrain.control.gamification.GameWorld;
import org.example.smartbrain.datahub.events.smartbrain.CreatePatient;

import java.util.Collection;
import java.util.List;

public class CreatePatientPlayerAdapter extends Adapter<CreatePatient> {

    public CreatePatientPlayerAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected Collection<GamificationEvent> doAdapt(CreatePatient event) {
        return List.of(createPlayer(event));
    }

    private CreatePlayer createPlayer(CreatePatient event) {
        CreatePlayer player = new CreatePlayer();
        player.id(event.dni());
        player.world(GameWorld.getId());
        player.health(100);
        player.groups(List.of("Patients"));
        return player;
    }
}
