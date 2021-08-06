package org.example.cinepolis.control.gamification.events;

import io.intino.gamification.events.MissionProgressEvent;

public class FixAsset extends MissionProgressEvent {

    public FixAsset(String worldId, String playerId) {
        super(worldId, playerId);
    }
}
