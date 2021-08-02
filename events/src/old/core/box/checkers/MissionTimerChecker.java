package old.core.box.checkers;

import old.core.box.CoreBox;
import old.core.box.helper.MissionHelper;
import static old.core.box.utils.TimeUtils.*;

import java.time.Instant;

public class MissionTimerChecker extends Checker {

    private Instant from;
    private Instant to;

    public MissionTimerChecker(CoreBox box) {
        super(box);
    }

    public void check(int amount, Scale scale) {

        to = currentInstant();
        from = previousInstant(to, scale, amount);

        MissionHelper helper = box.helper(MissionHelper.class);

        box.graph().worldList().stream()
                .filter(w -> w.match() != null)
                .forEach(w -> helper.failMissions(w, m -> m.expiration() != null && instantIsInRange(m.expiration(), from, to)));
    }
}
