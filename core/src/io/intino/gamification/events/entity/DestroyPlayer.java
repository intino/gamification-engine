package io.intino.gamification.events.entity;

import io.intino.gamification.events.GamificationEvent;

import java.time.Instant;

public class DestroyPlayer extends GamificationEvent {

    @Override
    public DestroyPlayer id(String id) {
        return null;
    }

    @Override
    public DestroyPlayer ts(Instant ts) {
        return null;
    }
}
