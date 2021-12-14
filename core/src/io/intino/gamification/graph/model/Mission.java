package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;

public final class Mission extends Node {

    private String description;
    private int priority;

    public Mission(String id) {
        super(id);
    }

    public String description() {
        return description;
    }

    public Mission description(String description) {
        this.description = description;
        return this;
    }

    public int priority() {
        return priority;
    }

    public Mission priority(int priority) {
        this.priority = priority;
        return this;
    }

    public Competition competition() {
        return parent();
    }
}
