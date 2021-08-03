package io.intino.gamification.graph.model;

import io.intino.gamification.events.EventCallback;
import io.intino.gamification.events.EventManager;
import io.intino.gamification.events.GamificationEvent;
import io.intino.gamification.utils.time.TimeUtils;

import java.time.Instant;

public final class Mission extends Node implements Comparable<Mission> {

    public static final long NO_EXPIRATION_TIME = Long.MAX_VALUE;

    private final String description;
    private final int priority;
    private final long expirationTimeSeconds;

    public Mission(String id, String description, int priority, long expirationTimeSeconds) {
        super(id);
        this.description = description;
        this.priority = priority;
        this.expirationTimeSeconds = expirationTimeSeconds;
    }

    public Mission(String id, String description, int priority) {
        super(id);
        this.description = description;
        this.priority = priority;
        this.expirationTimeSeconds = NO_EXPIRATION_TIME;
    }

    public Mission(String id, String description) {
        super(id);
        this.description = description;
        this.priority = 0;
        this.expirationTimeSeconds = NO_EXPIRATION_TIME;
    }

    public String description() {
        return this.description;
    }

    public int priority() {
        return this.priority;
    }

    public long expirationTimeSeconds() {
        return this.expirationTimeSeconds;
    }

    public boolean hasExpirationTime() {
        return expirationTimeSeconds != NO_EXPIRATION_TIME;
    }

    public boolean hasExpired(Instant referenceTs) {
        if(!hasExpirationTime()) return false;
        final Instant now = TimeUtils.currentInstant();
        final Instant expirationTs = referenceTs.plusSeconds(expirationTimeSeconds);
        return !now.isBefore(expirationTs);
    }

    @Override
    public int compareTo(Mission o) {
        return Integer.compare(priority, o.priority);
    }

    public <T extends GamificationEvent> void subscribe(Class<T> eventType, EventCallback<T> eventCallback) {
        EventManager.get().addEventCallback(eventType, eventCallback);
    }
}
