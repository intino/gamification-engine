package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.mission.MissionDifficulty;
import io.intino.gamification.core.box.events.mission.MissionType;
import io.intino.gamification.core.box.logic.CheckerHandler;

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

	public EventType event() {
		return EventType.valueOf(eventName);
	}

	public AbstractMission difficulty(MissionDifficulty difficulty) {
		difficultyName(difficulty.name());
		return this;
	}

	public AbstractMission type(MissionType type) {
		typeName(type.name());
		return this;
	}

	public AbstractMission event(EventType event) {
		eventName(event.name());
		return this;
	}

	public boolean check(GamificationEvent event, Player player) {
		return CheckerHandler.check(this, event, player);
	}
}