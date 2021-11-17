package io.intino.gamification.events;

import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.PlayerState;
import io.intino.gamification.graph.model.Season;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MissionProgressEventManager {

    private static MissionProgressEventManager instance;

    public static MissionProgressEventManager get() {
        return instance;
    }

    private final Map<String, MissionProgressEventCallback> eventCallback;

    public MissionProgressEventManager() {
        MissionProgressEventManager.instance = this;
        this.eventCallback = new ConcurrentHashMap<>();
    }

    public void setEventCallback(String missionId, MissionProgressEventCallback callback) {
        if(callback == null) return;
        eventCallback.put(missionId, callback);
    }

    public void callCallback(Mission mission, String playerId) {

        if(playerId == null) return;

        MissionProgressEventCallback callback = eventCallback.get(mission.id());
        if(callback == null) return;

        Season season = mission.competition().currentSeason();
        if(season == null) return;

        PlayerState playerState = season.playerStates().find(playerId);
        if(playerState == null) return;

        MissionAssignment missionAssignment = playerState.missionAssignments().stream()
                .filter(ma -> ma.missionId().equals(mission.id()))
                .findFirst().orElse(null);
        if(missionAssignment == null) return;

        callback.notify(missionAssignment.progress());
    }
}
