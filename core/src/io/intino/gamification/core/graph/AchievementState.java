package io.intino.gamification.core.graph;

public class AchievementState extends AbstractAchievementState {

	public AchievementState(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public io.intino.gamification.core.model.attributes.AchievementState state() {
		return io.intino.gamification.core.model.attributes.AchievementState.valueOf(stateName);
	}

	public AchievementState state(io.intino.gamification.core.model.attributes.AchievementState state) {
		stateName(state.name());
		return this;
	}
}