package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Fact;

public final class Reinforcement extends FactDefinition {

    public Reinforcement(String id, int points) {
        super(id, points);
    }

    public Reinforcement description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public void addFactTo(Match match) {
        match.addFact(new Fact()
                .season(match.round().season().id())
                .round(match.round().id())
                .type(Fact.StandardTypes.Reinforcement)
                .name(id())
                .points(points));
    }
}
