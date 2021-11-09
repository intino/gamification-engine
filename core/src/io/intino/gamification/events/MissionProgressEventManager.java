package io.intino.gamification.events;

import io.intino.gamification.graph.model.Competition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@SuppressWarnings("all")
public class MissionProgressEventManager {

    private static MissionProgressEventManager instance;

    public static MissionProgressEventManager get() {
        return instance;
    }

    private final Map<String, List<MissionProgressEventCallback<? extends MissionProgressEvent>>> eventCallbacks;

    public MissionProgressEventManager() {
        MissionProgressEventManager.instance = this;
        this.eventCallbacks = new ConcurrentHashMap<>();
    }

    public <T extends MissionProgressEvent> void addEventCallback(String eventType, MissionProgressEventCallback<T> callback) {
        if(callback == null) return;
        eventCallbacks.computeIfAbsent(eventType, k -> new ArrayList<>()).add(callback);
    }

    public void call(Competition competition, String missionId, String playerId) {
        MissionProgressEvent missionProgressEvent = new MissionProgressEvent(competition, missionId, playerId);
        invokeCallbacks(eventCallbacks.get(missionId), missionProgressEvent);
    }

    private void invokeCallbacks(List<MissionProgressEventCallback<? extends MissionProgressEvent>> callbacks, MissionProgressEvent missionProgressEvent) {
        if(callbacks == null) return;
        for(MissionProgressEventCallback<? extends MissionProgressEvent> callback : callbacks) {
            callback.castAndNotify(missionProgressEvent);
        }
    }
}
