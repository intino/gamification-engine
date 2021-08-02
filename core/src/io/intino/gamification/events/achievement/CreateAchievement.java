package io.intino.gamification.events.achievement;

import io.intino.gamification.events.CreateEvent;
import io.intino.gamification.events.GamificationEvent;

import java.time.Instant;

public class CreateAchievement extends GamificationEvent implements CreateEvent {

    private String customParams;

    @Override
    public CreateAchievement id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public CreateAchievement ts(Instant ts) {
        this.ts = ts;
        return this;
    }

    @Override
    public String customParams() {
        return customParams;
    }

    @Override
    public CreateAchievement customParams(String customParams) {
        this.customParams = customParams;
        return this;
    }
}
