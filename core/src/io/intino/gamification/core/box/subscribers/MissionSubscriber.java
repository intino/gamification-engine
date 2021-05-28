package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class MissionSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.mission.Mission> {
	private final CoreBox box;

	public MissionSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.mission.Mission event) {
		box.mounterFactory().handle(event);
	}
}