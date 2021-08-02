package old.core.graph;

public class MissionState extends AbstractMissionState {

	public MissionState(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public old.core.model.attributes.MissionState state() {
		return old.core.model.attributes.MissionState.valueOf(stateName);
	}

	public MissionState state(old.core.model.attributes.MissionState state) {
		stateName(state.name());
		return this;
	}
}