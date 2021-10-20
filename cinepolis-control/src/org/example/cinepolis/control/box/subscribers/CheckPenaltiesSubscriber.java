package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.dispatcher.CheckPenaltiesDispatcher;

public class CheckPenaltiesSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.gamification.CheckPenalties, String> {
	private final ControlBox box;

	public CheckPenaltiesSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.gamification.CheckPenalties event, String topic) {
		box.gamificationEventDispatchers().dispatcher(CheckPenaltiesDispatcher.class).dispatch(event);
	}
}