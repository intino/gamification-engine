package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class CreateItem extends GamificationEvent {

    public CreateItem() {
        super(CreateItem.class);
    }

    public CreateItem(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateItem(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String name() {
        return get("name");
    }

    public String world() {
        return get("world");
    }

    public CreateItem name(String name) {
        set("name", name);
        return this;
    }

    public CreateItem world(String world) {
        set("world", world);
        return this;
    }
}