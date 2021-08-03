package io.intino.gamification.events;

import io.intino.gamification.events.GamificationEvent;

import java.time.Instant;

public class CustomEvent extends GamificationEvent {

    @Override
    public GamificationEvent id(String id) {
        return this;
    }

    @Override
    public GamificationEvent ts(Instant ts) {
        return this;
    }
}
