package org.example.vaccine.mock;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.datahub.VaccineTerminal;
import org.example.vaccine.datahub.events.vaccines.CreateVaccine;

import java.util.Random;

public class VaccineMockGenerator {

    private final ControlBox box;
    private final VaccineTerminal terminal;
    private final Random random = new Random();

    public VaccineMockGenerator(ControlBox box) {
        this.box = box;
        this.terminal = box.terminal();
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
