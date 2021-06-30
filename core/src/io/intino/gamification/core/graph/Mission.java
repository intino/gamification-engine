package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.model.attributes.MissionDifficulty;
import io.intino.gamification.core.model.attributes.MissionType;
import io.intino.gamification.core.box.utils.TimeUtils;
import io.intino.gamification.core.box.checkers.CheckResult;
import io.intino.gamification.core.box.checkers.CheckerHandler;

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

	public EventType eventInvolved() {
		return EventType.valueOf(eventInvolvedName);
	}

	public AbstractMission difficulty(MissionDifficulty difficulty) {
		difficultyName(difficulty.name());
		return this;
	}

	public AbstractMission type(MissionType type) {
		typeName(type.name());
		return this;
	}

	public AbstractMission eventInvolved(EventType event) {
		eventInvolvedName(event.name());
		return this;
	}

	public CheckResult check(GamificationEvent event, Player player) {
		return CheckerHandler.check(this, event, player);
	}

	public <T extends GamificationEvent> void progressIf(CheckerHandler.Checker<T> checker) {
		CheckerHandler.progressIf(this, checker);
	}

	public boolean isActive() {
		return expiration == null || expiration.isAfter(TimeUtils.currentInstant());
	}
}