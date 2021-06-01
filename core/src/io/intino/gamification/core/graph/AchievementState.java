package io.intino.gamification.core.graph;

public class AchievementState extends AbstractAchievementState {

	public AchievementState(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public io.intino.gamification.core.box.events.enumerates.AchievementState status() {
		return io.intino.gamification.core.box.events.enumerates.AchievementState.valueOf(statusName);
	}

	public AchievementState status(io.intino.gamification.core.box.events.enumerates.AchievementState status) {
		statusName(status.name());
		return this;
	}
}