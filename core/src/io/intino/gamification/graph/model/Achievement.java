package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;

public class Achievement extends Node {

    private String description;
    private final Type type;

    public Achievement(String id, Type type) {
        super(id);
        this.type = type;
    }

    public Achievement(String id, Type type, String description) {
        super(id);
        this.type = type;
        this.description = description;
    }

    public String name() {
        return id();
    }

    public Type type() {
        return this.type;
    }

    public Achievement description(String description) {
        this.description = description;
        return this;
    }

    public String description() {
        return description;
    }

    @Override
    protected Competition parent() {
        String[] ids = parentIds();
        return GamificationGraph.get()
                .competitions().find(ids[0]);
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "name=" + id() +
                "description='" + description + '\'' +
                '}';
    }

    public enum Type {
        Mention, Milestone, Prize, Record
    }
}
