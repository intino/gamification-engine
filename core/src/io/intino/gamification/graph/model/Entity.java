package io.intino.gamification.graph.model;

public abstract class Entity extends WorldNode {

    public Entity(String world, String id) {
        super(world, id);
    }

    public final Match match() {
        return world().currentMatch();
    }

    //TODO: Las entidades deberían actualizarse con el mundo
    protected void onMatchBegin(Match match) {}
    protected void onMatchUpdate(Match match) {}
    protected void onMatchEnd(Match match) {}
}

