package org.example.smartbrain.control.box.graph.mounters;

import org.example.smartbrain.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.smartbrain.control.box.mounters.Mounter;

public class TestMounter implements Mounter {

	private final ControlBox box;

	public TestMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TestCreate event) {

	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TestEnd event) {

	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TestResponse event) {

	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TestBegin event) {

	}

	public void handle(Event event) {
		if	(event instanceof org.example.smartbrain.datahub.events.smartbrain.TestCreate) handle((org.example.smartbrain.datahub.events.smartbrain.TestCreate) event);
		else if(event instanceof org.example.smartbrain.datahub.events.smartbrain.TestEnd) handle((org.example.smartbrain.datahub.events.smartbrain.TestEnd) event);
		else if(event instanceof org.example.smartbrain.datahub.events.smartbrain.TestResponse) handle((org.example.smartbrain.datahub.events.smartbrain.TestResponse) event);
		else if(event instanceof org.example.smartbrain.datahub.events.smartbrain.TestBegin) handle((org.example.smartbrain.datahub.events.smartbrain.TestBegin) event);
	}
}