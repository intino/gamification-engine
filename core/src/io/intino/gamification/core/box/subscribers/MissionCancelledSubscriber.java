package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class MissionCancelledSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.MissionCancelled> {
	private final CoreBox box;

	public MissionCancelledSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.MissionCancelled event) {

	}
}