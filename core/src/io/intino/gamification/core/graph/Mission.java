package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.attributes.MissionState;
import io.intino.gamification.core.box.events.attributes.MissionDifficulty;
import io.intino.gamification.core.box.events.attributes.MissionType;

public class Mission extends AbstractMission {

	public Mission(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public MissionDifficulty difficulty() {
		return MissionDifficulty.valueOf(difficultyName);
	}

	public MissionType type() {
		return MissionType.valueOf(typeName);
	}

	public MissionState status() {
		return MissionState.valueOf(stateName);
	}

	public AbstractMission difficulty(MissionDifficulty difficulty) {
		difficultyName(difficulty.name());
		return this;
	}

	public AbstractMission type(MissionType type) {
		typeName(type.name());
		return this;
	}

	public AbstractMission status(MissionState status) {
		stateName(status.name());
		return this;
	}
}