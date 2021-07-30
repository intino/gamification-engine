package io.intino.gamification.events.entity;

import io.intino.gamification.events.CreateEvent;
import io.intino.gamification.events.GamificationEvent;

import java.time.Instant;
import java.util.List;

public class CreateNpc extends GamificationEvent implements CreateEvent {

    private String customParams;

    @Override
    public CreateNpc id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public CreateNpc ts(Instant ts) {
        this.ts = ts;
        return this;
    }

    @Override
    public String customParams() {
        return customParams;
    }

    @Override
    public CreateNpc customParams(String customParams) {
        this.customParams = customParams;
        return this;
    }
}
