package io.intino.gamification.events.match;

import io.intino.gamification.events.CreateEvent;
import io.intino.gamification.events.GamificationEvent;

import java.time.Instant;

public class BeginMatch extends GamificationEvent implements CreateEvent {

    private String customParams;

    @Override
    public BeginMatch id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public BeginMatch ts(Instant id) {
        return this;
    }

    @Override
    public String customParams() {
        return customParams;
    }

    @Override
    public BeginMatch customParams(String customParams) {
        this.customParams = customParams;
        return this;
    }
}
