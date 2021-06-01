package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.enumerates.EntityType;

import java.util.Map;

public class CreateEntity extends GamificationEvent {

    public CreateEntity() {
        super("Entity");
    }

    public CreateEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public EntityType type() {
        return getAsEnum("type", EntityType.class);
    }

    public Map<String, String> attributes() {
        return getAsMap("attributes");
    }

    public CreateEntity type(EntityType type) {
        set("type", type);
        return this;
    }

    public CreateEntity attributes(Map<String, String> attributes) {
        set("attributes", attributes);
        return this;
    }
}