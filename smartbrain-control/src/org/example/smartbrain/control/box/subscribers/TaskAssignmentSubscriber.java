package org.example.smartbrain.control.box.subscribers;

import org.example.smartbrain.control.box.ControlBox;

public class TaskAssignmentSubscriber implements java.util.function.Consumer<org.example.smartbrain.datahub.events.smartbrain.TaskAssignment> {

	private final ControlBox box;

	public TaskAssignmentSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.smartbrain.datahub.events.smartbrain.TaskAssignment event) {
		box.mounters().handle(event);
	}
}