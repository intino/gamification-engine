package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Fact;

public final class Foul extends FactDefinition {

    public Foul(String id, int points) {
        super(id, points);
    }

    @Override
    public void addFactTo(Match match) {
        match.addFact(new Fact()
                .season(match.round().season().id())
                .round(match.round().id())
                .type(Fact.StandardTypes.Foul)
                .name(id())
                .points(points));
    }
}
