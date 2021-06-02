package io.intino.gamification.core.graph;

public class MissionState extends AbstractMissionState {

	public MissionState(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public io.intino.gamification.core.box.events.mission.MissionState state() {
		return io.intino.gamification.core.box.events.mission.MissionState.valueOf(stateName);
	}

	public MissionState state(io.intino.gamification.core.box.events.mission.MissionState state) {
		stateName(state.name());
		return this;
	}
}