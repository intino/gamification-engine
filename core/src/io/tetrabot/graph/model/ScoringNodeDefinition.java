package io.tetrabot.graph.model;

public abstract class ScoringNodeDefinition<Self> extends Node {

    private int points = 0;

    public ScoringNodeDefinition(String id) {
        this(id, 0);
    }

    public ScoringNodeDefinition(String id, int points) {
        super(id);
        this.points = points;
    }

    public int points() {
        return points;
    }

    public Self points(int points) {
        this.points = points;
        return self();
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

    @SuppressWarnings("unchecked")
    public Self self() {
        return (Self) this;
    }
}
