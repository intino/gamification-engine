package io.intino.gamification.core.checker;

import io.intino.gamification.core.Core;
import io.intino.gamification.utils.time.Scale;
import io.intino.gamification.utils.time.TimeUtils;

import java.time.Instant;

public class TimeChecker extends Checker {

    private TimeUtils timeUtils;

    public TimeChecker(Core core) {
        super(core);
    }

    @Override
    public void check(int amount, Scale scale) {
        timeUtils = core.util(TimeUtils.class);

        Instant to = timeUtils.currentInstant();
        Instant from = timeUtils.previousInstant(to, scale, amount);

        //TODO CHECKEAMOS MISIONES Y LAS TERMINAMOS
        //TODO CHECKEAMOS PARTIDAS Y LAS TERMINAMOS
    }
}
