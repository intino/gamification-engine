package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class EntitySubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.entity.Entity> {
	private final CoreBox box;

	public EntitySubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.entity.Entity event) {
		box.mounterFactory().handle(event);
	}
}