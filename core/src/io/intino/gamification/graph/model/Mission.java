package io.intino.gamification.graph.model;

public final class Mission extends Node {

    private final int points;

    public Mission(String id, int points) {
        super(id);
        this.points = points;
    }

    public int points() {
        return points;
    }

    public Competition competition() {
        return parent();
    }
}
