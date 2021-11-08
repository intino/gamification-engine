package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;

public abstract class Entity extends Node {

    public Entity(String id) {
        super(id);
    }

    @Override
    protected Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0]);
    }
}

