package io.intino.gamification.util.data;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.graph.structure.ReadOnlyProperty;
import io.intino.gamification.util.Log;

import java.io.Serializable;

public final class Progress implements Serializable {

    private final int total;
    private final Property<Integer> current;
    private boolean failed;

    public Progress(int total) {
        this(total, 0);
    }

    public Progress(int total, int current) {
        if(total <= 0) {
            IllegalArgumentException e = new IllegalArgumentException("Total must be > 0");
            Log.error(e);
            throw e;
        }
        this.total = total;
        this.current = new Property<>();
        this.current.set(Math.min(total, current));
        this.failed = false;
    }

    public float get() {
        return this.current.get() / (float) this.total;
    }

    public Progress set(int current) {
        if(state() == State.InProgress) this.current.set(Math.min(total, current));
        return this;
    }

    public Progress increment() {
        return set(current.get() + 1);
    }

    public Progress fail() {
        this.failed = true;
        return this;
    }

    public Progress complete() {
        this.current.set(this.total);
        return this;
    }

    public State state() {
        if(isCompleted()) return State.Complete;
        if(!isFailed()) return State.InProgress;
        return State.Failed;
    }

    private boolean isCompleted() {
        return !failed && get() >= 1.0f;
    }

    private boolean isFailed() {
        return this.failed;
    }

    public int current() {
        return current.get();
    }

    public ReadOnlyProperty<Integer> currentProperty() {
        return current;
    }

    public int total() {
        return total;
    }

    public enum State {
        Complete, InProgress, Failed
    }
}
