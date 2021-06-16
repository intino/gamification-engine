package io.intino.gamification.core.box.events.entity;

public class DestroyNpc extends DestroyEntity {

    public DestroyNpc() {
        super(DestroyNpc.class);
    }

    public DestroyNpc(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public DestroyNpc(io.intino.alexandria.message.Message message) {
        super(message);
    }
}