package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.Log;

import java.util.Objects;

abstract class CompetitionNode extends Node {

    private String competitionId;

    public CompetitionNode(String id) {
        super(id);
    }

    void competitionId(String competitionId) {
        if(competitionId == null) {
            NullPointerException e = new NullPointerException("Competition id cannot be null");
            Log.error(e.getMessage(), e);
            throw e;
        }
        this.competitionId = competitionId;
    }

    public String competitionId() {
        return competitionId;
    }

    public final Competition competition() {
        return GamificationGraph.get().competitions().find(competitionId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompetitionNode other = (CompetitionNode) o;
        return Objects.equals(competitionId, other.competitionId) && id().equals(other.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(competition(), id());
    }
}
