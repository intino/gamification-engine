package org.example.control.box.subscribers;

import org.example.control.box.ControlBox;

public class VaccineSubscriber implements java.util.function.Consumer<org.example.datahub.events.example.Vaccine> {

	private final ControlBox box;

	public VaccineSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.datahub.events.example.Vaccine event) {

	}
}