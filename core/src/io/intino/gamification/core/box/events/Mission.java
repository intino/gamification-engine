package io.intino.gamification.core.box.events;

public class Mission extends GamificationEvent {

    public enum Difficulty {
        Easy, Medium, Hard
    }

    public enum Type {
        Primary, Secondary, Special
    }

    public Mission() {
        super("Mission");
    }

    public Mission(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public Mission(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String player() {
        return get("player");
    }

    public Difficulty difficulty() {
        return getAsEnum("difficulty", Difficulty.class);
    }

    public Type type() {
        return getAsEnum("type", Type.class);
    }

    public String description() {
        return get("description");
    }

    public Mission player(String player) {
        set("player", player);
        return this;
    }

    public Mission difficulty(Difficulty difficulty) {
        set("difficulty", difficulty);
        return this;
    }

    public Mission type(Type type) {
        set("type", type);
        return this;
    }

    public Mission description(String description) {
        set("description", description);
        return this;
    }
}