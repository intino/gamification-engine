package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class ActionSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.entity.Action> {
	private final CoreBox box;

	public ActionSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.entity.Action event) {
		box.mounterFactory().handle(event);
	}
}