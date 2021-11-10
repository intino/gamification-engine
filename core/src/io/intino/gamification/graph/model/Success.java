package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;

public class Success extends Node {

    private String description;
    private int points;

    public Success(String id, int points) {
        super(id);
        this.points = points;
    }

    public String description() {
        return description;
    }

    public Success description(String description) {
        this.description = description;
        return this;
    }

    public int points() {
        return points;
    }

    public Success points(int points) {
        this.points = points;
        return this;
    }

    public Competition competition() {
        return parent();
    }

    @Override
    public Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get().competitions().find(ids[0]);
    }

    @Override
    public String toString() {
        return "Success{" +
                "description='" + description + '\'' +
                ", points=" + points +
                '}';
    }
}
