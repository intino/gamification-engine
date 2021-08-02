package io.intino.gamification.events.world;

import io.intino.gamification.events.CreateEvent;
import io.intino.gamification.events.GamificationEvent;

import java.time.Instant;

public class CreateWorld extends GamificationEvent implements CreateEvent {

    private String customParams;

    @Override
    public CreateWorld id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public CreateWorld ts(Instant ts) {
        this.ts = ts;
        return this;
    }

    @Override
    public String customParams() {
        return customParams;
    }

    @Override
    public CreateWorld customParams(String customParams) {
        this.customParams = customParams;
        return this;
    }
}