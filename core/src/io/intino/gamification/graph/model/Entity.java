package io.intino.gamification.graph.model;

public abstract class Entity extends CompetitionNode {

    public Entity(String id) {
        super(id);
    }

    /*public final Round match() {
        return world().currentMatch();
    }*/

    protected void onMatchBegin(Round round) {}
    protected void onMatchEnd(Round round) {}
}

