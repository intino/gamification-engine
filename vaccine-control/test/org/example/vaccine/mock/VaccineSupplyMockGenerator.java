package org.example.vaccine.mock;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.graph.Hospital;

public class VaccineSupplyMockGenerator extends MockGenerator {

    public VaccineSupplyMockGenerator(ControlBox box) {
        super(box);
    }

    @Override
    public void execute() {
        for(Hospital hospital : box.graph().hospitalList()) {
            createSuppliesFor(hospital);
        }
    }

    private void createSuppliesFor(Hospital hospital) {
        final int count = random.nextInt();
        for(int i = 0;i < count;i++) {
            
        }
    }
}
