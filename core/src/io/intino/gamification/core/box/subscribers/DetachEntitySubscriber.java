package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class DetachEntitySubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.DetachEntity> {
	private final CoreBox box;

	public DetachEntitySubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.DetachEntity event) {
		box.mounterFactory().handle(event);
	}
}