package io.tetrabot.graph.model;

import io.tetrabot.util.data.Progress;
import io.tetrabot.util.time.TimeUtils;

import java.time.Instant;

import static io.tetrabot.util.data.Progress.State.InProgress;

public final class MissionAssignment extends Node {

    private final String missionId;
    private final Progress progress;
    private Instant startTime;
    private Instant expirationTime;
    private Instant endTime;

    public MissionAssignment(String id, String missionId, int stepsToComplete, Instant expirationTime) {
        super(id);
        this.missionId = missionId;
        this.progress = new Progress(stepsToComplete);
        this.startTime = TimeUtils.now();
        this.expirationTime = expirationTime;
    }

    public MissionAssignment(String id, String missionId, Progress progress, Instant startTime, Instant expirationTime) {
        super(id);
        this.missionId = missionId;
        this.progress = progress;
        this.startTime = startTime;
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

    public Instant startTime() {
        return startTime;
    }

    public MissionAssignment startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public Instant expirationTime() {
        return expirationTime;
    }

    public MissionAssignment expirationTime(Instant expirationTime) {
        this.expirationTime = expirationTime;
        return this;
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

    @Override
    public MissionAssignment copy() {
        return new MissionAssignment(id(), missionId, progress.copy(), startTime, expirationTime);
    }
}
