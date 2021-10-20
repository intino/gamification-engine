package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class MissionEndCheckerSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.gamification.MissionEndChecker, String> {
	private final ControlBox box;

	public MissionEndCheckerSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.gamification.MissionEndChecker event, String topic) {
		box.mounter().handle(event);
	}
}