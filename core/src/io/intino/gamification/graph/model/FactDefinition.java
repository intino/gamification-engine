package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.serializer.Json;

public abstract class FactDefinition extends Node {

    protected String description;
    protected final int points;

    FactDefinition(String id, int points) {
        super(id);
        this.points = points;
    }

    protected abstract void addFactTo(Match match);

    public String description() {
        return description;
    }

    public int points() {
        return points;
    }

    public final Competition competition() {
        return (Competition) parent();
    }

    @Override
    public String toString() {
        return "FactDefinition{" +
                "description='" + description + '\'' +
                ", points=" + points +
                ", competition=" + competition() +
                '}';
    }
}
