package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class AchievementSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.Achievement> {
	private final CoreBox box;

	public AchievementSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.Achievement event) {

	}
}