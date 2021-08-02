package io.intino.gamification.events.entity;

import io.intino.gamification.events.CreateEvent;
import io.intino.gamification.events.GamificationEvent;

import java.time.Instant;

public class CreatePlayer extends GamificationEvent implements CreateEvent {

    private String customParams;

    @Override
    public CreatePlayer id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public CreatePlayer ts(Instant ts) {
        this.ts = ts;
        return this;
    }

    @Override
    public String customParams() {
        return customParams;
    }

    @Override
    public CreatePlayer customParams(String customParams) {
        this.customParams = customParams;
        return this;
    }
}
