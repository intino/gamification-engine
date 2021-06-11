package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class DropItem extends GamificationEvent {

    public DropItem() {
        super(DropItem.class);
    }

    public DropItem(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public DropItem(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public String player() {
        return get("player");
    }

    public DropItem world(String world) {
        set("world", world);
        return this;
    }

    public DropItem player(String player) {
        set("player", player);
        return this;
    }
}