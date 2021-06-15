package org.example.vaccine.mock;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.datahub.VaccineTerminal;
import org.example.vaccine.datahub.events.vaccines.CreateHospital;

import java.util.List;
import java.util.Random;

public class HospitalMockGenerator {

    private final ControlBox box;
    private final VaccineTerminal terminal;
    private final Random random = new Random();

    public HospitalMockGenerator(ControlBox box) {
        this.box = box;
        this.terminal = box.terminal();
    }

    public void execute() {
        for(String location : locations()) {
            createHospitals(location, random.nextInt(5) + 1);
        }
    }

    private void createHospitals(String location, int count) {
        for(int i = 0;i < count;i++) {
            CreateHospital hospital = new CreateHospital();
            hospital.name(location + "_" + (i+1));
            hospital.location(location);
            terminal.publish(hospital);
        }
    }

    private List<String> locations() {
        return List.of("Las Palmas", "Santa Cruz", "Andalucia", "Madrid", "Cataluña", "Extremadura", "Galicia", "Aragon");
    }
}
