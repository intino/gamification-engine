package io.intino.gamification.events.mission;

import io.intino.gamification.events.CreateEvent;
import io.intino.gamification.events.GamificationEvent;

import java.time.Instant;

public class CreateMission extends GamificationEvent implements CreateEvent {

    private String customParams;

    @Override
    public CreateMission id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public CreateMission ts(Instant ts) {
        this.ts = ts;
        return this;
    }

    @Override
    public String customParams() {
        return customParams;
    }

    @Override
    public CreateMission customParams(String customParams) {
        this.customParams = customParams;
        return this;
    }
}
