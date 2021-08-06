package io.intino.gamification.graph.model;

public class Achievement extends Node {

    private final String description;
    private final int total;

    public Achievement(String id, String description, int total) {
        super(id);
        this.description = description;
        this.total = total;
    }

    public String description() {
        return description;
    }

    public int total() {
        return total;
    }
}
