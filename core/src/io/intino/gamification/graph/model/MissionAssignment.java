package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Progress;
import io.intino.gamification.util.time.TimeUtils;

import java.io.Serializable;
import java.time.Instant;

//RLP
public class MissionAssignment implements Comparable<MissionAssignment>, Serializable {

    private final String missionId;
    private final String playerId;
    private final Progress progress;
    private final Instant creationTime;

    public MissionAssignment(String missionId, String playerId, int total) {
        this.missionId = missionId;
        this.playerId = playerId;
        this.progress = new Progress(total);
        this.creationTime = TimeUtils.currentInstant();
    }

    public String missionId() {
        return missionId;
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
