package io.intino.gamification.core.box.mounters;

import io.intino.alexandria.event.Event;
import io.intino.gamification.core.box.CoreBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class MounterFactory {
	private Map<String, List<Mounter>> mounters = new HashMap<>();

	public MounterFactory(CoreBox box) {

	}

	public List<Mounter> mountersOf(Event event) {
		return mountersOf(event.toMessage().type());
	}

	public List<Mounter> mountersOf(String eventType) {
		return mounters.get(eventType);
	}

	public void handle(Event... events) {
		Stream.of(events).forEach(event -> {
			List<Mounter> mounters = mountersOf(event);
			if (mounters != null) mounters.forEach(m -> m.handle(event));
		});
	}
}