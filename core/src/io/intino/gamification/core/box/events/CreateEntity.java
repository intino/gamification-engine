package io.intino.gamification.core.box.events;

import java.util.Map;

public class CreateEntity extends GamificationEvent {

    public enum Type {
        Player, Npc, Enemy, Item
    }

    public CreateEntity() {
        super("Entity");
    }

    public CreateEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public Type type() {
        return getAsEnum("type", Type.class);
    }

    public Map<String, String> attributes() {
        return getAsMap("attributes");
    }

    public CreateEntity type(Type type) {
        set("type", type);
        return this;
    }

    public CreateEntity attributes(Map<String, String> attributes) {
        set("attributes", attributes);
        return this;
    }
}