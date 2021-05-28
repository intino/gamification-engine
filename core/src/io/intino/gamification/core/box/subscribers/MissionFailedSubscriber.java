package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class MissionFailedSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.MissionFailed> {
	private final CoreBox box;

	public MissionFailedSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.MissionFailed event) {

	}
}