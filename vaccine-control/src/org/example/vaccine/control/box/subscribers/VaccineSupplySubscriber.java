package org.example.vaccine.control.box.subscribers;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.box.adapter.CreateVaccineDoseAdapter;
import org.example.vaccine.datahub.events.vaccines.VaccineSupply;

public class VaccineSupplySubscriber implements java.util.function.Consumer<VaccineSupply> {

	private final ControlBox box;

	public VaccineSupplySubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(VaccineSupply event) {
		box.mounter().handle(event);
		new CreateVaccineDoseAdapter(box).adapt(event);
	}
}