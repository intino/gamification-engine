package io.intino.gamification.core.box.events.action;

public class Heal extends Action {

    public Heal() {
        super(Heal.class);
    }

    public Heal(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public Heal(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String entitySrc() {
        return get("entitySrc");
    }

    public Heal entitySrc(String entitySrc) {
        set("entitySrc", entitySrc);
        return this;
    }

    public Double healedHealth() {
        return getAsDouble("healedHealth");
    }

    public Heal healedHealth(Double healedHealth) {
        set("healedHealth", healedHealth);
        return this;
    }
}
