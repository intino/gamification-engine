package org.example.smartbrain.control.box.graph.mounters;

import org.example.smartbrain.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.smartbrain.control.box.mounters.Mounter;
import org.example.smartbrain.control.graph.Patient;

public class PatientMounter implements Mounter {

	private final ControlBox box;

	public PatientMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.CreatePatient event) {
		Patient patient = box.graph().create().patient(event.dni(), event.name(), event.lastname(), event.age());
		patient.save$();
	}

	public void handle(Event event) {
		if	(event instanceof org.example.smartbrain.datahub.events.smartbrain.CreatePatient) handle((org.example.smartbrain.datahub.events.smartbrain.CreatePatient) event);
	}
}