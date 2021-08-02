package old.core.graph;

import old.core.box.events.EventType;
import old.core.box.events.GamificationEvent;
import old.core.box.checkers.CheckResult;
import old.core.box.checkers.CheckerHandler;

public class Achievement extends AbstractAchievement {

	public Achievement(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public EventType eventInvolved() {
		return EventType.valueOf(eventInvolvedName);
	}

	public Achievement eventInvolved(EventType event) {
		eventInvolvedName(event.name());
		return this;
	}

	public CheckResult check(GamificationEvent event, Player player) {
		return CheckerHandler.check(this, event, player);
	}

	public <T extends GamificationEvent> void progressIf(CheckerHandler.Checker<T> checker) {
		CheckerHandler.progressIf(this, checker);
	}

    public CheckerHandler.Checker<? extends GamificationEvent> checker() {
		return CheckerHandler.checkerOf(this);
    }
}