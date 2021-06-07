package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class CreateNpc extends GamificationEvent {

    public CreateNpc() {
        super(CreateNpc.class);
    }

    public CreateNpc(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateNpc(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public CreateNpc world(String world) {
        set("world", world);
        return this;
    }
}