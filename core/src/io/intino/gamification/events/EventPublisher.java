package io.intino.gamification.events;

import io.intino.gamification.core.GamificationCore;

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
