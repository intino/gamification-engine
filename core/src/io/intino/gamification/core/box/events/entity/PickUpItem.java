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

    public String player() {
        return get("player");
    }

    public String item() {
        return get("item");
    }

    public PickUpItem player(String player) {
        set("player", player);
        return this;
    }

    public PickUpItem item(String item) {
        set("item", item);
        return this;
    }
}