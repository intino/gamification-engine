package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class MatchEndSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.match.MatchEnd> {
	private final CoreBox box;

	public MatchEndSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.match.MatchEnd event) {
		box.mounterFactory().handle(event);
	}
}