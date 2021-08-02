package old.core.graph;

import old.core.box.events.EventType;
import old.core.box.events.GamificationEvent;
import old.core.model.attributes.MissionDifficulty;
import old.core.model.attributes.MissionType;
import old.core.box.utils.TimeUtils;
import old.core.box.checkers.CheckResult;
import old.core.box.checkers.CheckerHandler;

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