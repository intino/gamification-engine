package io.intino.gamification.core.checker;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.utils.time.Scale;

public abstract class Checker {

    protected final GamificationCore core;

    public Checker(GamificationCore core) {
        this.core = core;
    }

    public abstract void check(int amount, Scale scale);
}
