package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;

public class Achievement extends Node {

    private String description;

    public Achievement(String id) {
        super(id);
    }

    public Achievement(String id, String description) {
        super(id);
        this.description = description;
    }

    public String name() {
        return id();
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
}
