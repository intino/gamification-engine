package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class DestroyEntitySubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.DestroyEntity> {
	private final CoreBox box;

	public DestroyEntitySubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.DestroyEntity event) {
		box.mounterFactory().handle(event);
	}
}