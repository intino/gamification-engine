package org.example.cinepolis.control.gamification.dispatcher;

import io.intino.alexandria.event.Event;
import org.example.cinepolis.control.box.ControlBox;

public abstract class Dispatcher<T extends Event> {

	protected final ControlBox box;

	public Dispatcher(ControlBox box) {
		this.box = box;
	}

	public abstract void dispatch(T event);
}
