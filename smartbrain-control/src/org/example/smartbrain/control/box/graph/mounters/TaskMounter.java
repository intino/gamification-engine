package org.example.smartbrain.control.box.graph.mounters;

import org.example.smartbrain.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.smartbrain.control.box.mounters.Mounter;

public class TaskMounter implements Mounter {
	private final ControlBox box;

	public TaskMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TaskAssignment event) {

	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TaskComplete event) {

	}

	public void handle(Event event) {
		if	(event instanceof org.example.smartbrain.datahub.events.smartbrain.TaskAssignment) handle((org.example.smartbrain.datahub.events.smartbrain.TaskAssignment) event);

		if	(event instanceof org.example.smartbrain.datahub.events.smartbrain.TaskComplete) handle((org.example.smartbrain.datahub.events.smartbrain.TaskComplete) event);
	}
}