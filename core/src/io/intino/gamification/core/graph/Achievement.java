package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.logic.CheckerHandler;

public class Achievement extends AbstractAchievement {

	public Achievement(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public AchievementType type() {
		return AchievementType.valueOf(typeName);
	}

	public EventType event() {
		return EventType.valueOf(eventName);
	}

	public Achievement type(AchievementType type) {
		typeName(type.name());
		return this;
	}

	public Achievement event(EventType event) {
		eventName(event.name());
		return this;
	}

	public boolean check(GamificationEvent event) {
		return CheckerHandler.check(this, event);
	}

	public void progressIf(CheckerHandler.Checker<? extends GamificationEvent> checker) {
		CheckerHandler.progressIf(this, checker);
	}
}