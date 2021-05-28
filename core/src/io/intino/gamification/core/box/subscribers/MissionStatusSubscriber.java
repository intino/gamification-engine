package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class MissionStatusSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.mission.MissionStatus> {
	private final CoreBox box;

	public MissionStatusSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.mission.MissionStatus event) {
		box.mounterFactory().handle(event);
	}
}