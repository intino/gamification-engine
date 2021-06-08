package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class DisableEntity extends GamificationEvent {

    public DisableEntity() {
        super(DisableEntity.class);
    }

    public DisableEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public DisableEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public DisableEntity world(String world) {
        set("world", world);
        return this;
    }
}