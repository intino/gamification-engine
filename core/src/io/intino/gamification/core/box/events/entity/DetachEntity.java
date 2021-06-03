package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class DetachEntity extends GamificationEvent {

    public DetachEntity() {
        super("DetachEntity");
    }

    public DetachEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public DetachEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String parent() {
        return get("parent");
    }

    public String child() {
        return get("child");
    }

    public DetachEntity parent(String parent) {
        set("parent", parent);
        return this;
    }

    public DetachEntity child(String child) {
        set("child", child);
        return this;
    }
}