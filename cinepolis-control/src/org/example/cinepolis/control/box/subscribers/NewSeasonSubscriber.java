package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.dispatcher.NewSeasonDispatcher;

public class NewSeasonSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.gamification.NewSeason, String> {
	private final ControlBox box;

	public NewSeasonSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.gamification.NewSeason event, String topic) {
		box.gamificationEventDispatchers().dispatcher(NewSeasonDispatcher.class).dispatch(event);
	}
}