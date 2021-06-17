package io.intino.gamification.core.box.events.action;

public class SetHealth extends Action {

    public SetHealth() {
        super(SetHealth.class);
    }

    public SetHealth(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public SetHealth(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public Double health() {
        return getAsDouble("health");
    }

    public SetHealth health(Double health) {
        set("health", health);
        return this;
    }
}
