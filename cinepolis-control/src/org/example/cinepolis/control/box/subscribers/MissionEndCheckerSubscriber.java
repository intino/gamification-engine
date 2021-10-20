package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.dispatcher.MissionEndCheckerDispatcher;

public class MissionEndCheckerSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.gamification.MissionEndChecker, String> {
	private final ControlBox box;

	public MissionEndCheckerSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.gamification.MissionEndChecker event, String topic) {
		box.gamificationEventDispatchers().dispatcher(MissionEndCheckerDispatcher.class).dispatch(event);
	}
}