package org.example.vaccine.control.box.subscribers;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.datahub.events.vaccines.CreateVaccine;

public class CreateVaccineSubscriber implements java.util.function.Consumer<CreateVaccine> {

	private final ControlBox box;

	public CreateVaccineSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(CreateVaccine event) {
		box.mounter().handle(event);
	}
}