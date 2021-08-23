package io.intino.gamification.graph.model;

public abstract class Entity extends WorldNode {

    public Entity(String worldId, String id) {
        super(worldId, id);
    }

    public final Match match() {
        return world().currentMatch();
    }

    protected void onMatchBegin(Match match) {}
    protected void onMatchUpdate(Match match) {}
    protected void onMatchEnd(Match match) {}
}

