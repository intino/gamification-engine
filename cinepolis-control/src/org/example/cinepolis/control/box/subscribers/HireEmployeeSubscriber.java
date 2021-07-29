package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class HireEmployeeSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.cinepolis.HireEmployee, String> {
	private final ControlBox box;

	public HireEmployeeSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.cinepolis.HireEmployee event, String topic) {
		box.mounter().handle(event);
		box.adapter().adapt(event);
	}
}