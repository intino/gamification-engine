package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class CheckPenaltiesSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.gamification.CheckPenalties, String> {
	private final ControlBox box;

	public CheckPenaltiesSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.gamification.CheckPenalties event, String topic) {
		box.gamificationEventDispatcher().dispatch(event);
	}
}