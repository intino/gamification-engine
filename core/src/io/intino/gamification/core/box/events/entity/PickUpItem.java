package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class PickUpItem extends GamificationEvent {

    public PickUpItem() {
        super(PickUpItem.class);
    }

    public PickUpItem(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public PickUpItem(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public PickUpItem world(String world) {
        set("world", world);
        return this;
    }

    public String player() {
        return get("player");
    }

    public PickUpItem player(String player) {
        set("player", player);
        return this;
    }
}