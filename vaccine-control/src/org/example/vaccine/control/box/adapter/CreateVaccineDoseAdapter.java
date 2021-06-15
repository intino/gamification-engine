package org.example.vaccine.control.box.adapter;

import io.intino.alexandria.event.Event;
import io.intino.gamification.core.box.events.entity.CreateItem;
import io.intino.gamification.core.box.events.entity.PickUpItem;
import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.box.gamification.GameWorld;
import org.example.vaccine.datahub.events.vaccines.VaccineSupply;

import java.util.ArrayList;
import java.util.List;

public class CreateVaccineDoseAdapter extends Adapter<VaccineSupply, Event> {

    public CreateVaccineDoseAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected List<Event> doAdapt(VaccineSupply event) {
        List<Event> vaccines = new ArrayList<>();
        for(int i = 0;i < event.units();i++) createVaccineDose(event, vaccines);
        return vaccines;
    }

    private void createVaccineDose(VaccineSupply event, List<Event> vaccines) {
        CreateItem item = new CreateItem();
        item.world(GameWorld.getID());
        item.id(event.vaccineName());
        item.health(100);
        item.enabled(true);

        PickUpItem bindToHospital = new PickUpItem();
        bindToHospital.world(GameWorld.getID());
        bindToHospital.player(event.hospitalName());

        vaccines.add(item);
        vaccines.add(bindToHospital);
    }
}
