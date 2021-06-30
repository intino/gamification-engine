package org.example.smartbrain.control.box.subscribers;

import org.example.smartbrain.control.box.ControlBox;
import org.example.smartbrain.control.box.adapter.TaskAssignAdapter;

public class TaskAssignSubscriber implements java.util.function.Consumer<org.example.smartbrain.datahub.events.smartbrain.TaskAssign> {

	private final ControlBox box;

	public TaskAssignSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.smartbrain.datahub.events.smartbrain.TaskAssign event) {
		box.mounters().handle(event);
		new TaskAssignAdapter(box).adapt(event);
	}
}