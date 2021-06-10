package org.example.vaccine.control.box.subscribers;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.box.graph.mounters.VaccineMounter;

public class CreateVaccineSubscriber implements java.util.function.Consumer<org.example.datahub.events.example.CreateVaccine> {

	private final ControlBox box;

	public CreateVaccineSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.datahub.events.example.CreateVaccine event) {
		new VaccineMounter(box).handle(event);
	}
}