package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class MatchBeginSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.match.MatchBegin> {
	private final CoreBox box;

	public MatchBeginSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.match.MatchBegin event) {
		box.mounterFactory().handle(event);
	}
}