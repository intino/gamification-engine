package io.intino.gamification.core.box.events.entity;

public class CreatePlayer extends CreateEntity {

    public CreatePlayer() {
        super(CreatePlayer.class);
    }

    public CreatePlayer(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreatePlayer(io.intino.alexandria.message.Message message) {
        super(message);
    }
}