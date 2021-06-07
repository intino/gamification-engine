package io.intino.gamification.core.box.events.entity;

import io.intino.gamification.core.box.events.GamificationEvent;

public class Action extends GamificationEvent {

    public Action() {
        super(Action.class);
    }

    public Action(String type) {
        super(type);
    }

    public Action(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String entitySrc() {
        return get("entitySrc");
    }

    public Action entitySrc(String entitySrc) {
        set("entitySrc", entitySrc);
        return this;
    }

    public String entityDest() {
        return get("entityDest");
    }

    public Action entityDest(String entityDest) {
        set("entityDest", entityDest);
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

    public static Action changeScore(String entity, int score) {
        return new Action("ChangeScore").entityDest(entity).attribute("score").attribute(String.valueOf(score));
    }

    public static Action attack(String entitySrc, String entityDest, int damage) {
        return new Action("Attack").entitySrc(entitySrc).entityDest(entityDest).attribute("health").attribute(String.valueOf(damage));
    }

    public static Action heal(String entitySrc, String entityDest, int health) {
        return new Action("Heal").entitySrc(entitySrc).entityDest(entityDest).attribute("health").attribute(String.valueOf(health));
    }
}