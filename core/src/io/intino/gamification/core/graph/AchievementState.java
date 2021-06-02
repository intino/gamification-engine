package io.intino.gamification.core.graph;

public class AchievementState extends AbstractAchievementState {

	public AchievementState(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public io.intino.gamification.core.box.events.attributes.AchievementState status() {
		return io.intino.gamification.core.box.events.attributes.AchievementState.valueOf(statusName);
	}

	public AchievementState status(io.intino.gamification.core.box.events.attributes.AchievementState status) {
		statusName(status.name());
		return this;
	}
}