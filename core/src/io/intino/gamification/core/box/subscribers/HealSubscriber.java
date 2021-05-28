package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class HealSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.Heal> {
	private final CoreBox box;

	public HealSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.Heal event) {

	}
}