package io.intino.gamification.core.box.events.entity;

public class CreateItem extends CreateEntity {

    public CreateItem() {
        super(CreateItem.class);
    }

    public CreateItem(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateItem(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String name() {
        return get("name");
    }

    public CreateItem name(String name) {
        set("name", name);
        return this;
    }
}