package org.example.vaccine.control.box.adapter;

import io.intino.alexandria.event.Event;
import org.example.vaccine.control.box.ControlBox;

import java.util.List;

public abstract class Adapter<T extends Event, R extends Event> {

    protected final ControlBox box;

    public Adapter(ControlBox box) {
        this.box = box;
    }

    public void adapt(T event) {
        doAdapt(event).forEach(box.engine().terminal()::feed);
    }

    protected abstract List<R> doAdapt(T event);
}
