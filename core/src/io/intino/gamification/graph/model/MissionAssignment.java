package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public final class MissionAssignment extends Node {

    private final String missionId;
    private final Progress progress;
    private final Instant creationTime;
    private final Instant expirationTime;
    private Instant endTime;

    public MissionAssignment(String id, String missionId, int stepsToComplete, Instant expirationTime) {
        super(id);
        this.missionId = missionId;
        this.progress = new Progress(stepsToComplete);
        this.creationTime = TimeUtils.now();
        this.expirationTime = expirationTime;
    }

    public MissionAssignment(String id, String missionId, Progress progress, Instant creationTime, Instant expirationTime) {
        super(id);
        this.missionId = missionId;
        this.progress = progress;
        this.creationTime = creationTime;
        this.expirationTime = expirationTime;
    }

    public boolean hasExpired() {
        if(expirationTime == null) return false;
        return !expirationTime.isAfter(TimeUtils.now());
    }

    void update() {
        if(progress.state() == InProgress)
            progress.increment();
        if(progress.state() != InProgress) {
            endTime = TimeUtils.now();
        }
    }

    void complete() {
        if(progress.state() == InProgress) return;
        progress.complete();
        endTime = TimeUtils.now();
    }

    void fail() {
        if(progress.state() == InProgress) return;
        progress.fail();
        endTime = TimeUtils.now();
    }

    public String missionId() {
        return missionId;
    }

    public float progress() {
        return progress.get();
    }

    public Progress.State state() {
        return progress.state();
    }

    public Instant creationTime() {
        return creationTime;
    }

    public Instant expirationTime() {
        return expirationTime;
    }

    public Instant endTime() {
        return endTime;
    }

    public MissionAssignment endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public PlayerState player() {
        return parent();
    }
}
