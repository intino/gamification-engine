package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.function.Function;

import static io.intino.gamification.util.data.Progress.State.*;

public abstract class MissionAssignment extends CompetitionNode {

    private final Progress progress;
    private PlayerState playerState;
    private final Instant creationTime;
    private final ExpirationTime expirationTime;
    private final Function<Instant, Integer> scoreFunction;

    protected MissionAssignment(String missionId, int stepsToComplete, ExpirationTime expirationTime, Function<Instant, Integer> scoreFunction) {
        super(missionId);
        this.progress = initProgress(stepsToComplete);
        this.creationTime = TimeUtils.currentInstant();
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
        return competition().missions().find(id());
    }

    public final PlayerState playerState() {
        return this.playerState;
    }

    public final void playerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public final Progress progress() {
        return progress;
    }

    public final ExpirationTime expirationTime() {
        return expirationTime;
    }

    public Integer score() {
        return scoreFunction.apply(creationTime);
    }

    protected void onProgressChange(Integer oldValue, Integer newValue) {}

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
