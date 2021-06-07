package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class CreateEnemy extends GamificationEvent {

    public CreateEnemy() {
        super(CreateEnemy.class);
    }

    public CreateEnemy(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateEnemy(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public CreateEnemy world(String world) {
        set("world", world);
        return this;
    }
}