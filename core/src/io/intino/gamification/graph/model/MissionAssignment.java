package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.time.TimeUtils;

import java.io.Serializable;
import java.time.Instant;

import static io.intino.gamification.util.data.Progress.State.*;

public class MissionAssignment implements Comparable<MissionAssignment>, Serializable {

    private final String worldId;
    private final String matchId;
    private final String missionId;
    private final String playerId;
    private final Progress progress;
    private final Instant creationTime;
    private Instant expirationTime;
    private boolean endsWithMatch;
    private boolean enabled;

    public MissionAssignment(String worldId, String matchId, String missionId, String playerId, int total, Instant expirationTime, boolean endsWithMatch) {
        this(worldId, matchId, missionId, playerId, total, TimeUtils.currentInstant(), expirationTime, endsWithMatch, true);
    }

    private MissionAssignment(String worldId, String matchId, String missionId, String playerId, int total, Instant creationTime, Instant expirationTime, boolean endsWithMatch, boolean enabled) {
        this.worldId = worldId;
        this.matchId = matchId;
        this.missionId = missionId;
        this.playerId = playerId;
        this.progress = initProgress(total);
        this.creationTime = creationTime;
        this.expirationTime = expirationTime;
        this.endsWithMatch = endsWithMatch;
        this.enabled = enabled;
    }

    private Progress initProgress(int total) {
        Progress progress = new Progress(total);
        progress.currentProperty()
                .addObserver((oldValue, newValue) -> mission().onProgressChange(MissionAssignment.this, oldValue, newValue));
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

    public final Instant expirationTime() {
        return this.expirationTime;
    }

    public final Instant expirationTime(Instant expirationTime) {
        return this.expirationTime = expirationTime;
    }

    public final boolean endsWithMatch() {
        return endsWithMatch;
    }

    public final void endsWithMatch(boolean endsWithMatch) {
        this.endsWithMatch = endsWithMatch;
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
            mission().onMissionComplete(this);
        } else if(newState == Failed) {
            mission().onMissionFail(this);
        } else if(newState == InProgress) {
            mission().onMissionIncomplete(this);
        }
        mission().onMissionEnd(this);
    }

    public final MissionAssignment copy() {
        //TODO: Se puede hacer copia?
        MissionAssignment missionAssignment = new MissionAssignment(worldId, matchId, missionId, playerId, progress.total(), creationTime, expirationTime, endsWithMatch, enabled);
        missionAssignment.progress.set(this.progress.current());

        return missionAssignment;
    }

    @Override
    public final String toString() {
        //TODO: REVISAR TIMES
        return "MissionAssignment{" +
                "missionId='" + missionId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", progress=" + progress +
                '}';
    }

    @Override
    public final int compareTo(MissionAssignment o) {
        return creationTime.compareTo(o.creationTime);
    }
}
