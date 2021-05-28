package io.intino.gamification.core.box.graph.mounters;

import io.intino.gamification.core.box.CoreBox;
import io.intino.alexandria.event.Event;

import io.intino.gamification.core.box.mounters.Mounter;

public class AttackMounter implements Mounter {
	private final CoreBox box;

	public AttackMounter(CoreBox box) {
		this.box = box;
	}



	public void handle(Event event) {

	}
}