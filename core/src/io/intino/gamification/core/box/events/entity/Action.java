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

    public String entitySrc() {
        return get("entitySrc");
    }

    public String entityDest() {
        return get("entityDest");
    }

    public String type() {
        return get("type");
    }

    public String attribute() {
        return get("attribute");
    }

    public String value() {
        return get("value");
    }

    public Action world(String world) {
        set("world", world);
        return this;
    }

    public Action entitySrc(String entitySrc) {
        set("entitySrc", entitySrc);
        return this;
    }

    public Action entityDest(String entityDest) {
        set("entityDest", entityDest);
        return this;
    }

    public Action type(String type) {
        set("type", type);
        return this;
    }

    public Action attribute(String attribute) {
        set("attribute", attribute);
        return this;
    }

    public Action value(String value) {
        set("value", value);
        return this;
    }

    public static Action changeScore(String world, String entity, String type, int score) {
        return new Action("ChangeScore")
                .world(world)
                .entityDest(entity)
                .type(type)
                .attribute("score")
                .value(String.valueOf(score));
    }

    public static Action changeScore(String world, String entity, int score) {
        return changeScore(world, entity, "ChangeScore", score);
    }

    public static Action attack(String world, String entitySrc, String entityDest, String type, double damage) {
        return new Action("Attack")
                .world(world)
                .entitySrc(entitySrc)
                .entityDest(entityDest)
                .type(type)
                .attribute("health")
                .value(String.valueOf(damage));
    }

    public static Action heal(String world, String entitySrc, String entityDest, String type, double healedHealth) {
        return new Action("Heal")
                .world(world)
                .entitySrc(entitySrc)
                .entityDest(entityDest)
                .type(type)
                .attribute("health")
                .value(String.valueOf(healedHealth));
    }
}