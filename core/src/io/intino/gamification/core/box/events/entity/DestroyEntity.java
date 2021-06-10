package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class DestroyEntity extends GamificationEvent {

    public DestroyEntity() {
        super(DestroyEntity.class);
    }

    public DestroyEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public DestroyEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public DestroyEntity world(String world) {
        set("world", world);
        return this;
    }
}