package io.intino.gamification.core.checker;

import io.intino.gamification.core.Core;
import io.intino.gamification.utils.time.Scale;

public abstract class Checker {

    protected final Core core;

    public Checker(Core core) {
        this.core = core;
    }

    public abstract void check(int amount, Scale scale);
}
