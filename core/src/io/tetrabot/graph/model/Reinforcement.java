package io.tetrabot.graph.model;

public final class Reinforcement extends ScoringNodeDefinition<Reinforcement> {

    public Reinforcement(String id) {
        super(id);
    }

    @Override
    public Reinforcement copy() {
        return new Reinforcement(id()).points(points());
    }

    public Reinforcement(String id, int points) {
        super(id, points);
    }
}
