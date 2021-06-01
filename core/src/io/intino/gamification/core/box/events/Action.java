package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.enumerates.ActionOperationType;

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

    public String srcEntity() {
        return get("srcEntity");
    }

    public String destEntity() {
        return get("destEntity");
    }

    public String destEntityAttribute() {
        return get("destEntityAttribute");
    }

    public String value() {
        return get("value");
    }

    public ActionOperationType operationType() {
        return getAsEnum("operationType", ActionOperationType.class);
    }

    public Action srcEntity(String srcEntity) {
        set("srcEntity", srcEntity);
        return this;
    }

    public Action destEntity(String destEntity) {
        set("destEntity", destEntity);
        return this;
    }

    public Action destEntityAttribute(String destEntityAttribute) {
        set("destEntityAttribute", destEntityAttribute);
        return this;
    }

    public Action value(String value) {
        set("value", value);
        return this;
    }

    public Action operationType(ActionOperationType operationType) {
        set("operationType", operationType);
        return this;
    }
}