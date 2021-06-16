package org.example.control.box.gamification;

import org.example.control.box.ControlBox;
import org.example.datahub.events.example.CreateVaccine;

public class CreateVaccineAdapter {

    private final ControlBox box;

    public CreateVaccineAdapter(ControlBox box) {
        this.box = box;
    }

    public void adapt(CreateVaccine vaccine) {

    }
}
