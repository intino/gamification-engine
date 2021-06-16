package org.example.smartbrain.control.box.subscribers;

import org.example.smartbrain.control.box.ControlBox;

public class TestBeginSubscriber implements java.util.function.Consumer<org.example.smartbrain.datahub.events.smartbrain.TestBegin> {
	private final ControlBox box;

	public TestBeginSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.smartbrain.datahub.events.smartbrain.TestBegin event) {

	}
}