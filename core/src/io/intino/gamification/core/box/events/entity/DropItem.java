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

    public String item() {
        return get("item");
    }

    public DropItem item(String item) {
        set("item", item);
        return this;
    }
}