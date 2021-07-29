package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class DismissEmployeeSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.cinepolis.DismissEmployee, String> {
	private final ControlBox box;

	public DismissEmployeeSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.cinepolis.DismissEmployee event, String topic) {
		box.mounter().handle(event);
		box.adapter().adapt(event);
	}
}