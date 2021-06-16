package org.example.smartbrain.control.box.subscribers;

import org.example.smartbrain.control.box.ControlBox;

public class CreatePatientSubscriber implements java.util.function.Consumer<org.example.smartbrain.datahub.events.smartbrain.CreatePatient> {
	private final ControlBox box;

	public CreatePatientSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.smartbrain.datahub.events.smartbrain.CreatePatient event) {

	}
}