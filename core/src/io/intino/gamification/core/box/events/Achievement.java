package io.intino.gamification.core.box.events;

public class Achievement extends GamificationEvent {

    public enum Type {
        Local, Global
    }

    public Achievement() {
        super("Achievement");
    }

    public Achievement(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public Achievement(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public Double description() {
        return getAsDouble("description");
    }

    public Type type() {
        return getAsEnum("type", Type.class);
    }

    public Achievement description(Double description) {
        set("description", description);
        return this;
    }

    public Achievement type(Type type) {
        set("type", type);
        return this;
    }
}