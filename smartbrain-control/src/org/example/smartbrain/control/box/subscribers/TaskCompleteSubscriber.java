package org.example.smartbrain.control.box.subscribers;

import org.example.smartbrain.control.box.ControlBox;
import org.example.smartbrain.control.box.adapter.TaskCompleteAdapter;

public class TaskCompleteSubscriber implements java.util.function.Consumer<org.example.smartbrain.datahub.events.smartbrain.TaskComplete> {

	private final ControlBox box;

	public TaskCompleteSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.smartbrain.datahub.events.smartbrain.TaskComplete event) {
		box.mounters().handle(event);
		new TaskCompleteAdapter(box).adapt(event);
	}
}