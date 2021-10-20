package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class NewSeasonSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.gamification.NewSeason, String> {
	private final ControlBox box;

	public NewSeasonSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.gamification.NewSeason event, String topic) {
		box.mounter().handle(event);
	}
}