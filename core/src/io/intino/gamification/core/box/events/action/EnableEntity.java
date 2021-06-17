package io.intino.gamification.core.box.events.action;

public class EnableEntity extends AbstractAction {

    public EnableEntity() {
        super(EnableEntity.class);
    }

    public EnableEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public EnableEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }
}