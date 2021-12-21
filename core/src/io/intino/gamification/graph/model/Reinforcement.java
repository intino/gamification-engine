package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Fact;

public final class Reinforcement extends FactDefinition {

    public Reinforcement(String id, int points) {
        super(id, points);
    }

    @Override
    public void addFactTo(Match match) {
        match.addFact(new Fact()
                .type(Fact.StandardTypes.Reinforcement)
                .name(id())
                .points(points));
    }
}
