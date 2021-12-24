package io.tetrabot.util.data;

import io.tetrabot.util.Log;

import java.io.Serializable;

public final class Progress implements Serializable {

    private final int total;
    private int value;
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
        this.value = Math.min(total, current);
        this.failed = false;
    }

    public float get() {
        return this.value / (float) this.total;
    }

    public Progress set(int value) {
        if(state() != State.InProgress) return this;
        if(value < 0) return this;
        this.value = Math.min(total, value);
        return this;
    }

    public void increment() {
        ++value;
    }

    public void fail() {
        failed = true;
    }

    public void complete() {
        value = total;
    }

    public State state() {
        if(isComplete()) return State.Complete;
        if(isFailed()) return State.Failed;
        return State.InProgress;
    }

    private boolean isComplete() {
        return !failed && get() >= 1.0f;
    }

    private boolean isFailed() {
        return this.failed;
    }

    public int current() {
        return value;
    }

    public int total() {
        return total;
    }

    public enum State {
        Complete, InProgress, Failed
    }
}
