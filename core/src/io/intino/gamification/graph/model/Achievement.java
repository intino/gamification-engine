package io.intino.gamification.graph.model;

public final class Achievement extends Node {

    private final Type type;
    private String description;

    public Achievement(String id, Type type) {
        super(id);
        this.type = type;
    }

    public String description() {
        return description;
    }

    public Achievement description(String description) {
        this.description = description;
        return this;
    }

    public Type type() {
        return type;
    }

    public Competition competition() {
        return parent();
    }

    public enum Type {
        Bonus, Milestone, Prize, Record
    }
}
