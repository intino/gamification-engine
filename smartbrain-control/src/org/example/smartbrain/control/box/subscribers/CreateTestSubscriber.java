package org.example.smartbrain.control.box.subscribers;

import org.example.smartbrain.control.box.ControlBox;

public class CreateTestSubscriber implements java.util.function.Consumer<org.example.smartbrain.datahub.events.smartbrain.TestCreate> {
	private final ControlBox box;

	public CreateTestSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.smartbrain.datahub.events.smartbrain.TestCreate event) {
		box.mounters().handle(event);
	}
}