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

    public String world() {
        return get("world");
    }

    public Action world(String world) {
        set("world", world);
        return this;
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

    public static Action changeScore(String world, String entity, int score) {
        return new Action("ChangeScore")
                .world(world)
                .entityDest(entity)
                .attribute("score")
                .value(String.valueOf(score));
    }

    public static Action attack(String world, String entitySrc, String entityDest, int damage) {
        return new Action("Attack")
                .world(world)
                .entitySrc(entitySrc)
                .entityDest(entityDest)
                .attribute("health")
                .value(String.valueOf(damage));
    }

    public static Action heal(String world, String entitySrc, String entityDest, int health) {
        return new Action("Heal")
                .world(world)
                .entitySrc(entitySrc)
                .entityDest(entityDest)
                .attribute("health")
                .value(String.valueOf(health));
    }
}