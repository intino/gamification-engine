package org.example.smartbrain.control.box.adapter;

import io.intino.alexandria.event.Event;
import io.intino.gamification.core.box.events.GamificationEvent;
import org.example.smartbrain.control.box.ControlBox;

import java.util.Collection;

public abstract class Adapter<T extends Event> {

    protected final ControlBox box;

    public Adapter(ControlBox box) {
        this.box = box;
    }

    public void adapt(T event) {
        doAdapt(event).forEach(box.engine().terminal()::feed);
    }

    protected abstract Collection<GamificationEvent> doAdapt(T event);
}
