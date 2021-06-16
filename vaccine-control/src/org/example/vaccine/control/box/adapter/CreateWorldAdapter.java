package org.example.vaccine.control.box.adapter;

import io.intino.gamification.core.box.events.world.CreateWorld;
import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.datahub.events.vaccines.CreateHospital;

import java.util.List;

public class CreateWorldAdapter extends Adapter<CreateHospital, CreateWorld> {

    public CreateWorldAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected List<CreateWorld> doAdapt(CreateHospital event) {
        CreateWorld world = new CreateWorld();
        world.id(event.location());
        return List.of(world);
    }
}
