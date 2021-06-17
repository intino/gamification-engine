package io.intino.gamification.core.box.actions;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.utils.TimeUtils;
import io.intino.gamification.core.box.helper.MissionHelper;

import java.time.Instant;

public class ExpireMissionsAction {

	public CoreBox box;
	private Instant from;
	private Instant to;

	public void execute() {

		from = TimeUtils.truncateTo(TimeUtils.currentInstant(), TimeUtils.Scale.D);
		to = TimeUtils.nextInstant(from, TimeUtils.Scale.D);

		MissionHelper helper = box.helper(MissionHelper.class);

		box.graph().worldList().stream()
				.filter(w -> w.match() != null)
				.forEach(w -> helper.failMissions(w, m -> m.to() != null && TimeUtils.instantIsInRange(m.to(), from, to)));
	}
}