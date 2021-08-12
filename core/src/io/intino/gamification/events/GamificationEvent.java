package io.intino.gamification.events;

import io.intino.gamification.util.serializer.Json;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;

public class GamificationEvent implements Comparable<GamificationEvent> {

    protected final Instant ts;

    public GamificationEvent() {
        this(TimeUtils.currentInstant());
    }

    public GamificationEvent(Instant ts) {
        this.ts = ts;
    }

    public Instant ts() {
        return this.ts;
    }

    @Override
    public String toString() {
        return Json.toJson(this);
    }

    @Override
    public int compareTo(GamificationEvent event) {
        return ts.compareTo(event.ts());
    }
}
