package org.example.smartbrain.control.box.subscribers;

import org.example.smartbrain.control.box.ControlBox;

public class TestResponseSubscriber implements java.util.function.Consumer<org.example.smartbrain.datahub.events.smartbrain.TestResponse> {
	private final ControlBox box;

	public TestResponseSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.smartbrain.datahub.events.smartbrain.TestResponse event) {

	}
}