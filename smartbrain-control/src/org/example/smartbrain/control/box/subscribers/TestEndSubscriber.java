package org.example.smartbrain.control.box.subscribers;

import org.example.smartbrain.control.box.ControlBox;

public class TestEndSubscriber implements java.util.function.Consumer<org.example.smartbrain.datahub.events.smartbrain.TestEnd> {
	private final ControlBox box;

	public TestEndSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.smartbrain.datahub.events.smartbrain.TestEnd event) {

	}
}