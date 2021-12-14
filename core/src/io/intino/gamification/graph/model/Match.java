package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.structure.Fact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Match extends Node {

    private final List<Fact> facts = new ArrayList<>();

    public Match(String playerId) {
        super(playerId);
    }

    public void addFact(Fact fact) {
        fact.competition(round().season().competition().id());
        fact.season(round().season().id());
        fact.round(round().id());
        facts.add(fact);
    }

    /**
     * Unmodifiable
     */
    public List<Fact> facts() {
        return Collections.unmodifiableList(facts);
    }

    public int score() {
        return facts.stream().mapToInt(Fact::points).sum();
    }

    public Round round() {
        return parent();
    }
}
