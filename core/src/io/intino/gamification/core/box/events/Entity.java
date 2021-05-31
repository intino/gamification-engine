package io.intino.gamification.core.box.events;

import java.util.Map;

public class Entity extends GamificationEvent {

    public enum Type {
        Player, Npc
    }

    public Entity() {
        super("Entity");
    }

    public Entity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public Entity(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public Double maxHealth() {
        return getAsDouble("maxHealth");
    }

    public Integer points() {
        return getAsInt("points");
    }

    public Type type() {
        return getAsEnum("type", Type.class);
    }

    public Map<String, String> attributes() {
        return getAsMap("attributes");
    }

    public Entity maxHealth(Double maxHealth) {
        set("maxHealth", maxHealth);
        return this;
    }

    public Entity points(Integer points) {
        set("points", points);
        return this;
    }

    public Entity type(Type type) {
        set("type", type);
        return this;
    }

    public Entity attributes(Map<String, String> attributes) {
        set("attributes", attributes);
        return this;
    }
}