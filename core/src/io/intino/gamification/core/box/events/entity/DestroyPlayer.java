package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.model.attributes.DestroyStrategy;

public class DestroyPlayer extends DestroyEntity {

    public DestroyPlayer() {
        super(DestroyPlayer.class);
    }

    public DestroyPlayer(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public DestroyPlayer(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public DestroyStrategy destroyStrategy() {
        return getAsEnum("destroyStrategy", DestroyStrategy.class);
    }

    public DestroyPlayer destroyStrategy(DestroyStrategy destroyStrategy) {
        set("destroyStrategy", destroyStrategy);
        return this;
    }
}