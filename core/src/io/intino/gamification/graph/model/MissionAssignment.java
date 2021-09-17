package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.time.TimeUtils;

import java.io.Serializable;
import java.time.Instant;

import static io.intino.gamification.util.data.Progress.State.*;

public final class MissionAssignment implements Comparable<MissionAssignment>, Serializable {

    private final String worldId;
    private final String missionId;
    private final String playerId;
    private final Progress progress;
    private final Instant creationTime;
    private final Instant expirationTime;

    public MissionAssignment(String worldId, String missionId, String playerId, int total, Instant expirationTime) {
        this.worldId = worldId;
        this.missionId = missionId;
        this.playerId = playerId;
        this.progress = new Progress(total);
        this.creationTime = TimeUtils.currentInstant();
        this.expirationTime = expirationTime;
    }

    public String woldId() {
       return worldId;
    }

    public World world() {
        return GamificationGraph.get().worlds().find(worldId);
    }

    public String missionId() {
        return missionId;
    }

    public Mission mission() {
        return world().missions().find(missionId);
    }

    public String playerId() {
        return playerId;
    }

    public Progress progress() {
        return progress;
    }

    public Instant creationTime() {
        return this.creationTime;
    }

    public Instant expirationTime() {
        return this.creationTime;
    }

    public void addPoints(int score) {
        Match match = world().currentMatch();
        if(match == null || !match.isAvailable()) return;
        match.player(playerId).addScore(score);
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

    public void update(Progress.State newState) {
        if(newState == Complete) {
            mission().onMissionComplete(this);
        } else if(newState == Failed) {
            mission().onMissionFail(this);
        } else if(newState == InProgress) {
            mission().onMissionIncomplete(this);
        }
        mission().onMissionEnd(this);
    }

    @Override
    public String toString() {
        //TODO: REVISAR TIMES
        return "MissionAssignment{" +
                "missionId='" + missionId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", progress=" + progress +
                '}';
    }

    @Override
    public int compareTo(MissionAssignment o) {
        return creationTime.compareTo(o.creationTime);
    }
}
