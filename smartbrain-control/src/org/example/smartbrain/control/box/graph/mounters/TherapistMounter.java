package org.example.smartbrain.control.box.graph.mounters;

import org.example.smartbrain.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.smartbrain.control.box.mounters.Mounter;
import org.example.smartbrain.control.graph.Therapist;

public class TherapistMounter implements Mounter {

	private final ControlBox box;

	public TherapistMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.CreateTherapist event) {
		Therapist therapist = box.graph().create().therapist(event.dni(), event.name(), event.lastname());
		therapist.save$();
	}

	public void handle(Event event) {
		if	(event instanceof org.example.smartbrain.datahub.events.smartbrain.CreateTherapist) handle((org.example.smartbrain.datahub.events.smartbrain.CreateTherapist) event);
	}
}