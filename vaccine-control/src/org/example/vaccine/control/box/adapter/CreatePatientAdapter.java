package org.example.vaccine.control.box.adapter;

import old.core.box.events.entity.CreateNpc;
import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.datahub.events.vaccines.Vaccination;

import java.util.List;

public class CreatePatientAdapter extends Adapter<Vaccination, CreateNpc> {

    public CreatePatientAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected List<CreateNpc> doAdapt(Vaccination event) {
        CreateNpc patient = new CreateNpc();
        patient.id(event.patientName());
        patient.health(1.0 / getVaccineMaxDoseCount(event.vaccineName()) * 100);
        return List.of(patient);
    }

    private float getVaccineMaxDoseCount(String vaccineName) {
        return box.graph().vaccineList(v -> v.name().equals(vaccineName)).findFirst().get().requiredDoseCount();
    }
}
