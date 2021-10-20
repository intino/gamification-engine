package org.example.cinepolis.control.gamification.dispatcher;

import org.example.cinepolis.control.box.ControlBox;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class Dispatchers {

	private final ControlBox box;

	private static Map<Class<? extends Dispatcher>, Dispatcher> builder = new HashMap<>();

	public Dispatchers(ControlBox box) {
		this.box = box;
		buildDispatchers();
	}

	private void buildDispatchers() {
		builder.put(CheckPenaltiesDispatcher.class, new CheckPenaltiesDispatcher(box));
		builder.put(NewMatchDispatcher.class, new NewMatchDispatcher(box));
		builder.put(MissionEndCheckerDispatcher.class, new MissionEndCheckerDispatcher(box));
		builder.put(NewSeasonDispatcher.class, new NewSeasonDispatcher(box));
	}

	public <T extends Dispatcher> T dispatcher(Class<T> clazz) {
		return (T) builder.get(clazz);
	}
}