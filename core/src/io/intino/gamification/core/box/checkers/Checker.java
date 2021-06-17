package io.intino.gamification.core.box.checkers;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;

public abstract class Checker {

    protected final CoreBox box;

    public Checker(CoreBox box) {
        this.box = box;
    }
}
