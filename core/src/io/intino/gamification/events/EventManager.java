package io.intino.gamification.events;

import io.intino.gamification.core.Core;
import io.intino.gamification.core.listener.SubscribedMissions;
import io.intino.gamification.model.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private final Core core;

    private final Map<Class<? extends GamificationEvent>, List<SubscribedMissions>> eventSubscribers;

    public EventManager(Core core) {
        this.core = core;
        this.eventSubscribers = new HashMap<>();
    }

    public <T extends GamificationEvent> void feed(T event) {
        eventSubscribers.get(event.getClass()).forEach(s -> s.notify(event));
    }

    public <T extends GamificationEvent> void subscribe(Class<T> clazz, SubscribedMissions action) {
        eventSubscribers.computeIfAbsent(clazz, k -> new ArrayList<>());
        eventSubscribers.get(clazz).add(action);
    }
}
