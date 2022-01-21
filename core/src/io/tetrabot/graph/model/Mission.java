package io.tetrabot.graph.model;

public final class Mission extends ScoringNodeDefinition<Mission> {

    public Mission(String id) {
        super(id);
    }

    @Override
    public Mission copy() {
        return new Mission(id()).points(points());
    }

    public Mission(String id, int points) {
        super(id, points);
    }
}
