package io.intino.gamification.events;

import io.intino.gamification.graph.model.*;
import io.intino.gamification.util.data.Progress;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager {

    private static EventManager instance;
    public static EventManager get() {
        return instance;
    }

    private final Map<String, EventCallback<?>> eventCallback;

    public EventManager() {
        EventManager.instance = this;
        this.eventCallback = new ConcurrentHashMap<>();
    }

    public void setEventCallback(String missionId, EventCallback<?> callback) {
        if(callback == null) return;
        eventCallback.put(missionId, callback);
    }

    @SuppressWarnings("unchecked")
    public void callCallback(Mission mission, String playerId) {

        MissionAssignment missionAssignment = missionAssignmentOf(mission, playerId);
        if (missionAssignment == null) return;

        EventCallback<Progress> callback = (EventCallback<Progress>) eventCallback.get(mission.id());
        if(callback == null) return;
        callback.notify(missionAssignment.progress());
    }

    @SuppressWarnings("unchecked")
    public void callCallback(FactDefinition factDefinition, String playerId) {

        Round.Match match = matchOf(factDefinition, playerId);
        if (match == null) return;

        EventCallback<Round.Match> callback = (EventCallback<Round.Match>) eventCallback.get(factDefinition.id());
        if(callback == null) return;
        callback.notify(match);
    }

    private MissionAssignment missionAssignmentOf(Mission mission, String playerId) {

        if(playerId == null) return null;

        Season season = mission.competition().currentSeason();
        if(season == null) return null;

        PlayerState playerState = season.playerStates().find(playerId);
        if(playerState == null) return null;

        return playerState.missionAssignments().stream()
                .filter(ma -> ma.missionId().equals(mission.id()))
                .findFirst().orElse(null);
    }

    private Round.Match matchOf(FactDefinition factDefinition, String playerId) {

        if(playerId == null) return null;

        Season season = factDefinition.competition().currentSeason();
        if(season == null) return null;

        Round round = season.currentRound();
        if(round == null) return null;

        return round.matches().find(playerId);
    }
}
