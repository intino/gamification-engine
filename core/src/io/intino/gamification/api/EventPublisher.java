package io.intino.gamification.api;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.events.GamificationEvent;

public class EventPublisher {

    private GamificationCore core;

    public EventPublisher(GamificationCore core) {
        this.core = core;
    }

    public EventPublisher publish(GamificationEvent event) {
        core.eventManager().publish(event);
        return this;
    }
}
