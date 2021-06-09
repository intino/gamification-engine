package io.intino.gamification.core.box.events.entity;

public class CreateEnemy extends CreateEntity {

    public CreateEnemy() {
        super(CreateEnemy.class);
    }

    public CreateEnemy(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateEnemy(io.intino.alexandria.message.Message message) {
        super(message);
    }
}