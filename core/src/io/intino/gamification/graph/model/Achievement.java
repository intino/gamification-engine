package io.intino.gamification.graph.model;

public class Achievement extends Node {

    private final String description;
    private final int stepsToComplete;

    public Achievement(String id, String description, int stepsToComplete) {
        super(id);
        this.description = description;
        this.stepsToComplete = stepsToComplete;
    }

    public final String description() {
        return description;
    }

    public final int total() {
        return stepsToComplete;
    }
}
