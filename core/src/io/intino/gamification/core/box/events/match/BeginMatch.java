package io.intino.gamification.core.box.events.match;

import io.intino.gamification.core.box.events.GamificationEvent;

public class BeginMatch extends GamificationEvent {

    public BeginMatch() {
        super("BeginMatch");
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
}