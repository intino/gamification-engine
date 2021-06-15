package org.example.control.box.gamification;

import org.example.control.box.ControlBox;
import org.example.datahub.events.example.CreateHospital;
import org.example.datahub.events.example.CreateVaccine;
import org.example.datahub.events.example.Vaccination;

public class Adapter {

    private final ControlBox box;

    public Adapter(ControlBox box) {
        this.box = box;
    }

    public void adapt(CreateHospital event) {

    }

    public void adapt(CreateVaccine event) {

    }

    public void adapt(Vaccination event) {

    }
}
