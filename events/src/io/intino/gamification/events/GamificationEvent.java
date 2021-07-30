package io.intino.gamification.events;

import java.time.Instant;

public abstract class GamificationEvent {

    protected String id;
    protected Instant ts;

    public String id() {
        return id;
    }

    public Instant ts() {
        return ts;
    }

    public abstract GamificationEvent id(String id);

    public abstract GamificationEvent ts(Instant ts);
}
