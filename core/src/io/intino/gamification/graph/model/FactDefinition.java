package io.intino.gamification.graph.model;

public abstract class FactDefinition extends Node {

    protected final int points;

    FactDefinition(String id, int points) {
        super(id);
        this.points = points;
    }

    protected abstract void addFactTo(Match match);

    public int points() {
        return points;
    }

    public final Competition competition() {
        return parent();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id() +
                ", points=" + points +
                '}';
    }
}
