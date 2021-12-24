package io.tetrabot.graph.model;

public final class Achievement extends Node {

    private final Type type;

    public Achievement(String id, Type type) {
        super(id);
        this.type = type;
    }

    public Type type() {
        return type;
    }

    public Competition competition() {
        return parent();
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id() +
                ", type='" + type + '\'' +
                '}';
    }

    public enum Type {
        Bonus, Milestone, Prize, Record
    }
}
