package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class EnableEntity extends GamificationEvent {

    public EnableEntity() {
        super(EnableEntity.class);
    }

    public EnableEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public EnableEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public EnableEntity world(String world) {
        set("world", world);
        return this;
    }
}