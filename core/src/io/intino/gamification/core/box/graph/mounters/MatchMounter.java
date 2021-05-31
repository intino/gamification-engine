package io.intino.gamification.core.box.graph.mounters;

import io.intino.gamification.core.box.CoreBox;
import io.intino.alexandria.event.Event;

import io.intino.gamification.core.box.mounters.Mounter;

public class MatchMounter implements Mounter {

	private final CoreBox box;

	public MatchMounter(CoreBox box) {
		this.box = box;
	}

	public void handle(Event event) {

	}
}