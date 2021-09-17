package io.intino.gamification.events;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.util.Log;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("all")
public class EventManager {

    private static EventManager instance;

    public static EventManager get() {
        return instance;
    }

    private final GamificationCore core;
    private final Map<Class<? extends GamificationEvent>, List<EventCallback<? extends GamificationEvent>>> eventCallbacks;

    public EventManager(GamificationCore core) {
        if(core == null) {
            NullPointerException e = new NullPointerException("GamificationCore cannot be null");
            Log.error(e);
            throw e;
        }
        EventManager.instance = this;
        this.core = core;
        this.eventCallbacks = new ConcurrentHashMap<>();
    }

    public void publish(GamificationEvent event) {
        if(event == null) return;
        invokeEventCallbacks(event, eventCallbacks.get(event.getClass()));
    }

    public <T extends GamificationEvent> void addEventCallback(Class<T> eventClass, EventCallback<T> callback) {
        if(callback == null) return;
        eventCallbacks.computeIfAbsent(eventClass, c -> new ArrayList<>()).add(callback);
    }

    private void invokeEventCallbacks(GamificationEvent event, List<EventCallback<? extends GamificationEvent>> callbacks) {
        if(callbacks == null) return;
        for(EventCallback callback : callbacks) {
            callback.notify(event);
        }
    }
}
