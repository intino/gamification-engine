package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Fact;
import io.intino.gamification.util.time.TimeUtils;

public class Reinforcement extends FactDefinition {

    protected Reinforcement(String id, int points) {
        super(id, points);
    }

    public final Reinforcement description(String description) {
        this.description = description;
        return this;
    }

    @Override
    protected final void addFactTo(Round.Match match) {
        match.addFact(new Fact()
                .season(match.round().season().id())
                .round(match.round().id())
                .type(Fact.StandardTypes.Reinforcement)
                .name(id())
                .points(points));
    }

    @Override
    public String toString() {
        return "Success{" +
                "description='" + description + '\'' +
                ", points=" + points +
                '}';
    }
}