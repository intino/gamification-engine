package io.intino.gamification.core.box.events.entity;

public class CreateNpc extends CreateEntity {

    public CreateNpc() {
        super(CreateNpc.class);
    }

    public CreateNpc(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateNpc(io.intino.alexandria.message.Message message) {
        super(message);
    }
}