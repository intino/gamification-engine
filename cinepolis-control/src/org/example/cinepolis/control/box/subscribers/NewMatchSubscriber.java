package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class NewMatchSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.gamification.NewMatch, String> {
	private final ControlBox box;

	public NewMatchSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.gamification.NewMatch event, String topic) {
		box.mounter().handle(event);
	}
}