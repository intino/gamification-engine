package io.tetrabot.graph.model;

public final class Foul extends ScoringNodeDefinition<Foul> {

    public Foul(String id) {
        super(id);
    }

    public Foul(String id, int points) {
        super(id, points);
    }
}
