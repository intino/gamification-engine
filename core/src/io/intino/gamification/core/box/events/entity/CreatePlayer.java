package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class CreatePlayer extends GamificationEvent {

    public CreatePlayer() {
        super(CreatePlayer.class);
    }

    public CreatePlayer(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreatePlayer(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public CreatePlayer world(String world) {
        set("world", world);
        return this;
    }
}