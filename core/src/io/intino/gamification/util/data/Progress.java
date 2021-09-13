package io.intino.gamification.util.data;

import io.intino.gamification.util.Logger;

import java.io.Serializable;

public final class Progress implements Serializable {

    private final int total;
    private int current;
    private boolean failed;

    public Progress(int total) {
        this(total, 0);
    }

    public Progress(int total, int current) {
        if(total <= 0) {
            IllegalArgumentException e = new IllegalArgumentException("Total must be > 0");
            Logger.error(e);
            throw e;
        }
        this.total = total;
        this.current = Math.min(total, current);
        this.failed = false;
    }

    public float get() {
        return this.current / (float) this.total;
    }

    public Progress set(int current) {
        if(state() == State.InProgress) this.current = Math.min(total, current);
        return this;
    }

    public Progress increment() {
        if(state() == State.InProgress) this.current = Math.min(total, current + 1);
        return this;
    }

    public Progress fail() {
        this.failed = true;
        return this;
    }

    public State state() {
        if(complete()) return State.Complete;
        if(!failed()) return State.InProgress;
        return State.Failed;
    }

    private boolean complete() {
        return !failed && get() >= 1.0f;
    }

    private boolean failed() {
        return this.failed;
    }

    public enum State {
        Complete, InProgress, Failed
    }
}
