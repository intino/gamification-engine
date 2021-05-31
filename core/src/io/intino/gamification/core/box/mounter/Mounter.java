package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;

public abstract class Mounter {

    protected final CoreBox box;

    public Mounter(CoreBox box) {
        this.box = box;
    }

    protected abstract void handle(GamificationEvent event);
}
