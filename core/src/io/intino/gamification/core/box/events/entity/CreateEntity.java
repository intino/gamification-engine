package io.intino.gamification.core.box.events.entity;

import io.intino.alexandria.event.Event;
import io.intino.alexandria.message.Message;
import io.intino.gamification.core.box.events.GamificationEvent;

import java.util.List;

public abstract class CreateEntity extends GamificationEvent {

    public CreateEntity(Class<? extends GamificationEvent> clazz) {
        super(clazz);
    }

    public CreateEntity(Event event) {
        super(event);
    }

    public CreateEntity(Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public CreateEntity world(String world) {
        set("world", world);
        return this;
    }

    public Double health() {
        return getAsDouble("health");
    }

    public CreateEntity health(double health) {
        set("health", health);
        return this;
    }

    public Boolean enabled() {
        return getAsBoolean("enabled");
    }

    public CreateEntity enabled(boolean enabled) {
        set("enabled", enabled);
        return this;
    }

    public List<String> groups() {
        return getAsList("groups");
    }

    public CreateEntity groups(List<String> groups) {
        set("groups", groups);
        return this;
    }
}
