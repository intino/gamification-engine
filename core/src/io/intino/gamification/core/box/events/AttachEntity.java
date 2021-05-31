package io.intino.gamification.core.box.events;

public class AttachEntity extends GamificationEvent {

    public AttachEntity() {
        super("AttachEntity");
    }

    public AttachEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public AttachEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String parent() {
        return get("parent");
    }

    public String child() {
        return get("child");
    }

    public AttachEntity parent(String parent) {
        set("parent", parent);
        return this;
    }

    public AttachEntity child(String child) {
        set("child", child);
        return this;
    }
}