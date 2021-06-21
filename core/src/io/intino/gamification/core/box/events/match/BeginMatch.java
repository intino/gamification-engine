package io.intino.gamification.core.box.events.match;

import io.intino.gamification.core.box.events.GamificationEvent;

import java.time.Instant;

public class BeginMatch extends GamificationEvent {

    public BeginMatch() {
        super(BeginMatch.class);
    }

    public BeginMatch(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public BeginMatch(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public BeginMatch world(String world) {
        set("world", world);
        return this;
    }

    public Instant expiration() {
        return getAsInstant("expiration");
    }

    public BeginMatch expiration(Instant expiration) {
        set("expiration", expiration);
        return this;
    }

    public Boolean reboot() {
        return getAsBoolean("reboot");
    }

    public BeginMatch reboot(boolean reboot) {
        set("reboot", reboot);
        return this;
    }
}