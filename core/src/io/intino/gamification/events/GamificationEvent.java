package io.intino.gamification.events;

import io.intino.gamification.utils.Json;
import io.intino.gamification.utils.time.TimeUtils;

import java.time.Instant;
import java.util.Objects;

public class GamificationEvent {

    private String id;
    private Instant ts = TimeUtils.currentInstant();

    public GamificationEvent() {
    }

    public GamificationEvent(String id, Instant ts) {
        this.id = id;
        this.ts = ts;
    }

    public String id() {
        return this.id;
    }

    public GamificationEvent id(String id) {
        this.id = id;
        return this;
    }

    public Instant ts() {
        return this.ts;
    }

    public GamificationEvent ts(Instant ts) {
        this.ts = ts;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamificationEvent that = (GamificationEvent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return Json.toJson(this);
    }
}
