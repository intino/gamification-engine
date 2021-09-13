package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.time.TimeUtils;

import java.io.Serializable;
import java.time.Instant;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public final class MissionAssignment implements Comparable<MissionAssignment>, Serializable {

    private final String worldId;
    private final String missionId;
    private final String playerId;
    private final Progress progress;
    private final Instant creationTime;

    public MissionAssignment(String worldId, String missionId, String playerId, int total) {
        this.worldId = worldId;
        this.missionId = missionId;
        this.playerId = playerId;
        this.progress = new Progress(total);
        this.creationTime = TimeUtils.currentInstant();
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

    public void assignPoints(int score) {
        Match match = world().currentMatch();
        if(match == null || !match.isAvailable()) return;
        match.player(playerId).addScore(score);
    }

    public void checkExpiration() {
        if(mission().hasExpired(creationTime)) fail();
    }

    void fail() {
        if(progress().state() == InProgress) progress().fail();
    }

    @Override
    public String toString() {
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
