package io.intino.gamification.core.box.actions;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.utils.TimeUtils;
import io.intino.gamification.core.graph.Match;

import java.time.Instant;

public class ExpireMatchesAction {

	public CoreBox box;
	private Instant from;
	private Instant to;

	public void execute() {

		from = TimeUtils.truncateTo(TimeUtils.currentInstant(), TimeUtils.Scale.D);
		to = TimeUtils.nextInstant(from, TimeUtils.Scale.D);

		box.graph().activeMatches().stream()
				.filter(m -> m.to() != null && TimeUtils.instantIsInRange(m.to(), from, to))
				.forEach(this::endMatch);
	}

	private void endMatch(Match match) {
		box.terminal().feed(EventBuilder.endMatch(match.worldId(), match.id()));
		if(match.reboot()) box.terminal().feed(EventBuilder.beginMatch(match.worldId(), match.from(), match.to()));
	}
}