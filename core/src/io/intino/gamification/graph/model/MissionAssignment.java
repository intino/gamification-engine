package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.function.Function;

import static io.intino.gamification.util.data.Progress.State.*;

public abstract class MissionAssignment extends Node {

    private final String missionId;
    private final Progress progress;
    private final Instant creationTime;
    private final ExpirationTime expirationTime;
    private Instant endTime;
    private String description;

    protected MissionAssignment(String id, String missionId, int stepsToComplete, ExpirationTime expirationTime) {
        super(id);
        this.missionId = missionId;
        this.progress = initProgress(stepsToComplete);
        this.creationTime = TimeUtils.now();
        this.expirationTime = expirationTime;
    }

    private Progress initProgress(int stepsToComplete) {
        Progress progress = new Progress(stepsToComplete);
        progress.currentProperty().addObserver(this::onProgressChange);
        return progress;
    }

    boolean hasExpired() {
        if(expirationTime == null) return false;
        return !expirationTime.isAfter(TimeUtils.now());
    }

    void update(Progress.State newState) {
        if(newState == Complete) {
            onMissionComplete();
        } else if(newState == Failed) {
            onMissionFail();
        } else if(newState == InProgress) {
            onMissionIncomplete();
        }
        onMissionEnd();
    }

    void fail() {
        if(progress().state() == InProgress) {
            progress.fail();
            update(Failed);
        }
    }

    void complete() {
        if(progress().state() == InProgress) {
            progress.complete();
            update(Complete);
        }
    }

    MissionAssignment copy() {
        MissionAssignment missionAssignment = getCopy();
        missionAssignment.progress.set(this.progress.current());
        return missionAssignment;
    }

    public final String missionId() {
        return missionId;
    }

    public final Progress progress() {
        return progress;
    }

    public Progress.State state() {
        return progress.state();
    }

    public final Instant creationTime() {
        return creationTime;
    }

    public final ExpirationTime expirationTime() {
        return expirationTime;
    }

    public Instant endTime() {
        return endTime;
    }

    public MissionAssignment endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public String description() {
        return description;
    }

    public MissionAssignment description(String description) {
        this.description = description;
        return this;
    }

    protected void onProgressChange(int oldValue, int newValue) {}

    protected void onMissionComplete() {}
    protected void onMissionFail() {}
    protected void onMissionIncomplete() {}
    protected void onMissionEnd() {}

    protected abstract MissionAssignment getCopy();

    public final PlayerState player() {
        return parent();
    }

    @Override
    public final PlayerState parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0])
                .seasons().find(ids[1])
                .playerStates().find(ids[2]);
    }

    public static class ExpirationTime {

        private final Instant instant;

        public ExpirationTime() {
            this(null);
        }

        public ExpirationTime(Instant instant) {
            this.instant = instant;
        }

        public Instant instant() {
            return instant;
        }

        public boolean endsWithMatch() {
            return instant == null;
        }

        public long getEpochSecond() {
            return instant == null ? 0 : instant.getEpochSecond();
        }

        public int compareTo(Instant otherInstant) {
            return instant == null && otherInstant == null ? 0 : -1;
        }

        public boolean isAfter(Instant otherInstant) {
            return instant != null && instant.isAfter(otherInstant);
        }

        public boolean isBefore(Instant otherInstant) {
            return instant != null && instant.isBefore(otherInstant);
        }

        @Override
        public String toString() {
            return "ExpirationTime{" +
                    "instant=" + instant +
                    ", endsWithMatch=" + endsWithMatch() +
                    '}';
        }
    }
}
