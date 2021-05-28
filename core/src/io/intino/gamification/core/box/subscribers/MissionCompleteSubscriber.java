package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class MissionCompleteSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.MissionComplete> {
	private final CoreBox box;

	public MissionCompleteSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.MissionComplete event) {

	}
}