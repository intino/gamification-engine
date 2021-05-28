package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class AttackSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.Attack> {
	private final CoreBox box;

	public AttackSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.Attack event) {

	}
}