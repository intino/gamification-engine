package io.intino.gamification.core.box.actions;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.helper.Time;
import io.intino.gamification.core.box.logic.FailMission;

import java.time.Instant;

public class ExpireMissionsAction {

	public CoreBox box;
	private Instant from;
	private Instant to;

	public void execute() {

		to = Time.truncateTo(Time.currentInstant(), Time.Scale.D);
		from = Time.previousInstant(to, Time.Scale.D);

		box.graph().worldList().stream()
				.filter(w -> w.match() != null)
				.forEach(w -> FailMission.get(box)
						.failMissions(w, m -> m.to() != null && Time.instantIsInRange(m.to(), from, to)));
	}
}