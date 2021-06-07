package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class CreateEntity extends GamificationEvent {

    public CreateEntity() {
        super(CreateEntity.class);
    }

    public CreateEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public EntityType type() {
        return getAsEnum("type", EntityType.class);
    }

    public String world() {
        return get("world");
    }

    public CreateEntity type(EntityType type) {
        set("type", type);
        return this;
    }

    public CreateEntity world(String world) {
        set("world", world);
        return this;
    }
}