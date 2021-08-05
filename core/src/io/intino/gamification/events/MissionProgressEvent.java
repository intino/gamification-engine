package io.intino.gamification.events;

import io.intino.gamification.util.time.TimeUtils;

public abstract class MissionProgressEvent extends GamificationEvent {

    private final String worldId;
    private final String playerId;

    public MissionProgressEvent(String worldId, String playerId) {
        super(TimeUtils.currentInstant());
        this.worldId = worldId;
        this.playerId = playerId;
    }

    public String worldId() {
        return worldId;
    }

    public String playerId() {
        return playerId;
    }
}
