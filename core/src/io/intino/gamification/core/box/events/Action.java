package io.intino.gamification.core.box.events;

public class Action extends GamificationEvent {

    public Action() {
        super("Action");
    }

    public Action(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public Action(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String entity() {
        return get("entity");
    }

    public Action entity(String entity) {
        set("entity", entity);
        return this;
    }

    public String attribute() {
        return get("attribute");
    }

    public Action attribute(String attribute) {
        set("attribute", attribute);
        return this;
    }

    public String value() {
        return get("value");
    }

    public Action value(String value) {
        set("value", value);
        return this;
    }
}