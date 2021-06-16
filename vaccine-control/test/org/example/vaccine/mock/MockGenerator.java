package org.example.vaccine.mock;

import org.example.vaccine.control.box.ControlBox;

public class MockGenerator {

    public static void main(String[] args) {
        ControlBox box = ControlBoxMock.getControlBox();
        new HospitalMockGenerator(box).execute();
        new VaccineMockGenerator(box).execute();
    }
}


