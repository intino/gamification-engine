package org.example.vaccine.mock;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.datahub.VaccineTerminal;

import java.util.Random;

public abstract class MockGenerator {

    public static void main(String[] args) {
        ControlBox box = ControlBoxMock.getControlBox();
        new HospitalMockGenerator(box).execute();
        new VaccineMockGenerator(box).execute();
    }

    protected final ControlBox box;
    protected final VaccineTerminal terminal;
    protected final Random random = new Random();

    public MockGenerator(ControlBox box) {
        this.box = box;
        this.terminal = box.terminal();
    }

    public abstract void execute();
}


