package io.intino.gamification.core.box.mounters;

import io.intino.alexandria.event.Event;

public interface Mounter {
	void handle(Event event);
}