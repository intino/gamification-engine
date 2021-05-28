package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class AttachEntitySubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.entity.AttachEntity> {
	private final CoreBox box;

	public AttachEntitySubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.entity.AttachEntity event) {
		box.mounterFactory().handle(event);
	}
}