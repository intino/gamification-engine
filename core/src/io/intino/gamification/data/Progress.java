package io.intino.gamification.data;

public final class Progress {

    private float progress;
    private boolean failed;

    public Progress() {
        this(0.0f);
    }

    public Progress(float progress) {
        this.progress = progress;
        this.failed = false;
    }

    public float get() {
        return this.progress;
    }

    public Progress set(float progress) {
        this.progress = Math.max(0.0f, Math.min(progress, 1.0f));
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
        return !failed && progress >= 1.0f;
    }

    private boolean failed() {
        return this.failed;
    }

    public enum State {
        Complete, InProgress, Failed
    }
}
