package io.intino.gamification.core.box.mounter.builder;

import io.intino.gamification.core.box.CoreBox;

public abstract class Filter {

    protected final CoreBox box;

    public Filter(CoreBox box) {
        this.box = box;
    }
}
