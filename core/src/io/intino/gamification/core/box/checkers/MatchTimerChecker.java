package io.intino.gamification.core.box.checkers;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.graph.Match;

import java.time.Instant;
import java.util.UUID;

import static io.intino.gamification.core.box.utils.TimeUtils.*;

public class MatchTimerChecker extends Checker {

    private Instant from;
    private Instant to;

    public MatchTimerChecker(CoreBox box) {
        super(box);
    }

    public void check(int amount, Scale scale) {

        to = currentInstant();
        from = previousInstant(to, scale, amount);

        box.graph().activeMatches().stream()
                .filter(m -> m.to() != null && instantIsInRange(m.to(), from, to))
                .forEach(this::endMatch);
    }

    private void endMatch(Match match) {
        box.terminal().feed(EventBuilder.endMatch(match.worldId(), match.id()));
        if(match.reboot()) box.terminal().feed(EventBuilder.beginMatch(match.worldId(), idOf(match), match.from(), match.to()));
    }

    private String idOf(Match match) {
        return match.id().substring(0, match.id().indexOf("_"));
    }
}
