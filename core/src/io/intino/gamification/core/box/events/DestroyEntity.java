package io.intino.gamification.core.box.events;

public class DestroyEntity extends GamificationEvent {

    public DestroyEntity() {
        super("DestroyEntity");
    }

    public DestroyEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public DestroyEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }
}