package io.intino.gamification.core.box.subscribers;

import io.intino.gamification.core.box.CoreBox;

public class AchievementStatusSubscriber implements java.util.function.Consumer<io.intino.gamification.model.events.gamification.achievement.AchievementStatus> {

	private final CoreBox box;

	public AchievementStatusSubscriber(CoreBox box) {
		this.box = box;
	}

	public void accept(io.intino.gamification.model.events.gamification.achievement.AchievementStatus event) {
		box.mounterFactory().handle(event);
	}
}