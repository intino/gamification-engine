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
    private final Function<MissionAssignment, Integer> scoreFunction;

    protected MissionAssignment(String id, String missionId, int stepsToComplete, ExpirationTime expirationTime, Function<MissionAssignment, Integer> scoreFunction) {
        super(id);
        this.missionId = missionId;
        this.progress = initProgress(stepsToComplete);
        this.creationTime = TimeUtils.currentInstant();
        this.expirationTime = expirationTime;
        this.scoreFunction = scoreFunction;
    }

    protected MissionAssignment(String id, String missionId, Progress progress, Instant creationTime, ExpirationTime expirationTime, Function<MissionAssignment, Integer> scoreFunction) {
        super(id);
        this.missionId = missionId;
        this.progress = progress;
        this.creationTime = creationTime;
        this.expirationTime = expirationTime;
        this.scoreFunction = scoreFunction;
    }

    boolean hasExpired() {
        if(expirationTime == null) return false;
        return !expirationTime.isAfter(TimeUtils.currentInstant());
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

    private Progress initProgress(int stepsToComplete) {
        Progress progress = new Progress(stepsToComplete);
        progress.currentProperty().addObserver(this::onProgressChange);
        return progress;
    }

    public final Mission mission() {
        return parent().parent().parent().missions().find(missionId);
    }

    public final String missionId() {
        return missionId;
    }

    public final Progress progress() {
        return progress;
    }

    public final Instant creationTime() {
        return creationTime;
    }

    public final ExpirationTime expirationTime() {
        return expirationTime;
    }

    public Integer score() {
        return scoreFunction.apply(this);
    }

    public PlayerState player() {
        return parent();
    }

    @Override
    public PlayerState parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0])
                .seasons().find(ids[1])
                .playerStates().find(ids[2]);
    }

    protected void onProgressChange(int oldValue, int newValue) {}

    protected void onMissionComplete() {}
    protected void onMissionFail() {}
    protected void onMissionIncomplete() {}
    protected void onMissionEnd() {}

    protected abstract MissionAssignment getCopy();

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
