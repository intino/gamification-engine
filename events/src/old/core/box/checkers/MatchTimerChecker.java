package old.core.box.checkers;

import old.core.box.CoreBox;
import old.core.box.events.EventBuilder;
import old.core.graph.Match;
import old.core.model.Achievement;

import java.time.Instant;

import static old.core.box.utils.TimeUtils.*;

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
                .forEach(this::restartMatch);
    }

    private void restartMatch(Match match) {
        box.terminal().feed(EventBuilder.endMatch(match.worldId(), match.id()));
        if(match.reboot()) {
            box.terminal().feed(EventBuilder.beginMatch(match.worldId(), originalId(match), match.from(), match.to()));
            match.achievements().forEach(a -> {
                Achievement achievement = (Achievement) box.terminal().feed(EventBuilder.createAchievement(a, match.worldId()));
                achievement.progressIf(a.checker());
            });
        }
    }

    private String originalId(Match match) {
        return match.id().substring(0, match.id().indexOf("_"));
    }
}
