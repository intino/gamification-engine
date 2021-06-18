package io.intino.gamification.core.box.checkers;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.helper.MissionHelper;
import io.intino.gamification.core.box.utils.TimeUtils;

import java.time.Instant;

public class MissionTimerChecker extends Checker {

    private Instant from;
    private Instant to;

    public MissionTimerChecker(CoreBox box) {
        super(box);
    }

    public void check() {

        from = TimeUtils.truncateTo(TimeUtils.currentInstant(), TimeUtils.Scale.H);
        to = TimeUtils.nextInstant(from, TimeUtils.Scale.H);

        MissionHelper helper = box.helper(MissionHelper.class);

        box.graph().worldList().stream()
                .filter(w -> w.match() != null)
                .forEach(w -> helper.failMissions(w, m -> m.to() != null && TimeUtils.instantIsInRange(m.to(), from, to)));
    }
}
