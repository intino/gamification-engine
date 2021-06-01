package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.enumerates.MissionStatus;
import io.intino.gamification.core.box.events.enumerates.MissionDifficulty;
import io.intino.gamification.core.box.events.enumerates.MissionType;

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

	public MissionStatus status() {
		return MissionStatus.valueOf(statusName);
	}

	public AbstractMission difficulty(MissionDifficulty difficulty) {
		difficultyName(difficulty.name());
		return this;
	}

	public AbstractMission type(MissionType type) {
		typeName(type.name());
		return this;
	}

	public AbstractMission status(MissionStatus status) {
		statusName(status.name());
		return this;
	}
}