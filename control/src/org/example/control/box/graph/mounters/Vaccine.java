package org.example.control.box.graph.mounters;

import org.example.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.control.box.mounters.Mounter;
import org.example.control.graph.AbstractGraph;
import org.example.control.graph.ControlGraph;

public class Vaccine implements Mounter {

	private final ControlBox box;

	public Vaccine(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.datahub.events.example.Vaccine event) {
		ControlGraph graph = box.graph();

		if(graph.hospitalList().contains(event.hospital())) {

		}

		AbstractGraph.Create create = graph.create();
	}

	public void handle(Event event) {
		if	(event instanceof org.example.datahub.events.example.Vaccine) handle((org.example.datahub.events.example.Vaccine) event);
	}
}