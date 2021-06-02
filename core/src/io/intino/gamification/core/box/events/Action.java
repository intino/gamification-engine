package io.intino.gamification.core.box.events;

public class Action extends GamificationEvent {

    public Action() {
        super("Action");
    }

    public Action(String type) {
        super(type);
    }

    public Action(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String entity() {
        return get("entity");
    }

    public Action entity(String entity) {
        set("entity", entity);
        return this;
    }

    public String attribute() {
        return get("attribute");
    }

    public Action attribute(String attribute) {
        set("attribute", attribute);
        return this;
    }

    public String value() {
        return get("value");
    }

    public Action value(String value) {
        set("value", value);
        return this;
    }

    public static Action changeLevel(String entity, int level) {
        return new Action("ChangeLevel").entity(entity).attribute("level").attribute(String.valueOf(level));
    }

    public static Action changeScore(String entity, int score) {
        return new Action("ChangeScore").entity(entity).attribute("score").attribute(String.valueOf(score));
    }

    public static Action changeHealth(String entity, int health) {
        return new Action("ChangeHealth").entity(entity).attribute("health").attribute(String.valueOf(health));
    }

    public static Action attack(String entity, int damage) {
        return new Action("Attack").entity(entity).attribute("health").attribute(String.valueOf(damage));
    }

    public static Action heal(String entity, int health) {
        return new Action("Heal").entity(entity).attribute("health").attribute(String.valueOf(health));
    }
}