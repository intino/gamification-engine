package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.logic.CheckerHandler;

public class Achievement extends AbstractAchievement {

	public Achievement(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public EventType event() {
		return EventType.valueOf(eventName);
	}

	public Achievement event(EventType event) {
		eventName(event.name());
		return this;
	}

	public CheckerHandler.AchievementProgress check(GamificationEvent event, Player player) {
		return CheckerHandler.check(this, event, player);
	}

	public void progressIf(CheckerHandler.Checker<? extends GamificationEvent> checker) {
		CheckerHandler.progressIf(this, checker);
	}
}