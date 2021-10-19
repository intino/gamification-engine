package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.time.TimeUtils;

import java.io.Serializable;
import java.sql.Time;
import java.time.Instant;

import static io.intino.gamification.util.data.Progress.State.*;

public abstract class MissionAssignment implements Comparable<MissionAssignment>, Serializable {

    private String worldId;
    private String matchId;
    private String missionId;
    private String playerId;
    private final Progress progress;
    private final Instant creationTime;
    private ExpirationTime expirationTime;
    private boolean enabled;

    protected MissionAssignment(String missionId, int stepsToComplete, ExpirationTime expirationTime) {
        this.missionId = missionId;
        this.progress = initProgress(stepsToComplete);
        this.creationTime = TimeUtils.currentInstant();
        this.expirationTime = expirationTime;
        this.enabled = true;
    }

//    protected MissionAssignment(String worldId, String matchId, String missionId, String playerId, int stepsToComplete, ExpirationTime expirationTime) {
//        this(worldId, matchId, missionId, playerId, stepsToComplete, TimeUtils.currentInstant(), expirationTime, true);
//    }

    private MissionAssignment(String worldId, String matchId, String missionId, String playerId, int stepsToComplete,
                              Instant creationTime, ExpirationTime expirationTime, boolean enabled) {
        this.worldId = worldId;
        this.matchId = matchId;
        this.missionId = missionId;
        this.playerId = playerId;
        this.progress = initProgress(stepsToComplete);
        this.creationTime = creationTime;
        this.expirationTime = expirationTime;
        this.enabled = enabled;
    }

    private Progress initProgress(int stepsToComplete) {
        Progress progress = new Progress(stepsToComplete);
        progress.currentProperty().addObserver(this::onProgressChange);
        return progress;
    }

    public final String woldId() {
       return worldId;
    }

    public final World world() {
        return GamificationGraph.get().worlds().find(worldId);
    }

    public final String matchId() {
        return matchId;
    }

    public final Match match() {
        return world().match(matchId);
    }

    public final String missionId() {
        return missionId;
    }

    public final Mission mission() {
        return world().missions().find(missionId);
    }

    public final String playerId() {
        return playerId;
    }

    public final Progress progress() {
        return progress;
    }

    public final Instant creationTime() {
        return this.creationTime;
    }

    public final ExpirationTime expirationTime() {
        return this.expirationTime;
    }

    public final ExpirationTime expirationTime(ExpirationTime expirationTime) {
        return this.expirationTime = expirationTime;
    }

    MissionAssignment worldId(String worldId) {
        this.worldId = worldId;
        return this;
    }

    MissionAssignment matchId(String matchId) {
        this.matchId = matchId;
        return this;
    }

    MissionAssignment missionId(String missionId) {
        this.missionId = missionId;
        return this;
    }

    MissionAssignment playerId(String playerId) {
        this.playerId = playerId;
        return this;
    }

    public final boolean enabled() {
        return enabled;
    }

    public final void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final boolean hasExpired() {
        if(expirationTime == null) return false;
        return !expirationTime.isAfter(TimeUtils.currentInstant());
    }

    public final Match.PlayerState playerState() {
        return match().player(playerId);
    }

    public final void fail() {
        if(progress().state() == InProgress) {
            progress.fail();
            update(Failed);
        }
    }

    public final void complete() {
        if(progress().state() == InProgress) {
            progress.complete();
            update(Complete);
        }
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

    protected void onMissionComplete() {}
    protected void onMissionFail() {}
    protected void onMissionIncomplete() {}
    protected void onMissionEnd() {}

    protected void onProgressChange(Integer oldValue, Integer newValue) {}

     MissionAssignment copy() {
        MissionAssignment missionAssignment = getCopy();
        missionAssignment.progress.set(this.progress.current());

        return missionAssignment;
    }

    protected abstract MissionAssignment getCopy();

    @Override
    public String toString() {
        return "MissionAssignment{" +
                "worldId='" + worldId + '\'' +
                ", matchId='" + matchId + '\'' +
                ", missionId='" + missionId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", progress=" + progress +
                ", creationTime=" + creationTime +
                ", expirationTime=" + expirationTime +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public final int compareTo(MissionAssignment o) {
        return creationTime.compareTo(o.creationTime);
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
