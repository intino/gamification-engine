package org.example.cinepolis.control.box.graph.mounters;

import org.example.cinepolis.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.cinepolis.control.box.mounters.Mounter;

public class EmployeeMounter implements Mounter {
	private final ControlBox box;

	public EmployeeMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.HireEmployee event) {

	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.DismissEmployee event) {

	}

	public void handle(Event event) {
		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.HireEmployee) handle((org.example.cinepolis.datahub.events.cinepolis.HireEmployee) event);

		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.DismissEmployee) handle((org.example.cinepolis.datahub.events.cinepolis.DismissEmployee) event);
	}
}