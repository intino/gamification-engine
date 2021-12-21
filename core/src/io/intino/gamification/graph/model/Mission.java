package io.intino.gamification.graph.model;

public final class Mission extends Node {

    private int points = 100;

    public Mission(String id) {
        super(id);
    }

    public Mission(String id, int points) {
        super(id);
        this.points = points;
    }

    public int points() {
        return points;
    }

    public Mission points(int points) {
        this.points = points;
        return this;
    }

    public Competition competition() {
        return parent();
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id() +
                ", points=" + points +
                '}';
    }
}
