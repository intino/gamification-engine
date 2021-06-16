package org.example.vaccine.mock;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.datahub.events.vaccines.CreateVaccine;

public class VaccineMockGenerator extends MockGenerator {

    public VaccineMockGenerator(ControlBox box) {
        super(box);
    }

    public void execute() {
        System.out.println("Creating vaccines...");
        int count = random.nextInt(20);
        for(int i = 0;i < count;i++) createVaccine(i);
    }

    private void createVaccine(int index) {
        CreateVaccine vaccine = new CreateVaccine();
        vaccine.name("Vaccine_" + index);
        vaccine.illness("Illness_" + random.nextInt(index + 1));
        vaccine.effectiveness(random.nextDouble());
        vaccine.requiredDoseCount(random.nextInt(3)+1);

        terminal.publish(vaccine);
    }
}
