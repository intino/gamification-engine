package org.example.vaccine.control.box.adapter;

import io.intino.gamification.core.box.events.action.AbstractAction;
import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.datahub.events.vaccines.Vaccination;

import java.util.List;

public class UpdatePatientAdapter extends Adapter<Vaccination, AbstractAction> {

    public UpdatePatientAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected List<AbstractAction> doAdapt(Vaccination event) {
        return List.of(AbstractAction.heal(
                        location(event.hospitalName()),
                        event.hospitalName(),
                        event.patientName(),
                        "",
                        1.0 / getVaccineMaxDoseCount(event.vaccineName()) * 100)
        );
    }

    private float getVaccineMaxDoseCount(String vaccineName) {
        return box.graph().vaccineList(v -> v.name().equals(vaccineName)).findFirst().get().requiredDoseCount();
    }

    private String location(String hospitalName) {
        return box.graph().hospitalList(h -> h.name().equals(hospitalName)).findFirst().get().location();
    }
}
