package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.achievement.AchievementType;

public class Achievement extends AbstractAchievement {

	public Achievement(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public AchievementType type() {
		return AchievementType.valueOf(typeName);
	}

	public Achievement type(AchievementType type) {
		typeName(type.name());
		return this;
	}

}