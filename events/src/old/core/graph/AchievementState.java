package old.core.graph;

public class AchievementState extends AbstractAchievementState {

	public AchievementState(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public old.core.model.attributes.AchievementState state() {
		return old.core.model.attributes.AchievementState.valueOf(stateName);
	}

	public AchievementState state(old.core.model.attributes.AchievementState state) {
		stateName(state.name());
		return this;
	}
}