package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.graph.model.Actor;

public class Cinema extends Actor {

    private boolean weeklyVisitFlag;
    private int health;

    public Cinema(String worldId, String id) {
        super(worldId, id);
        this.weeklyVisitFlag = false;
        this.health = 100;
    }

    public boolean weeklyVisitFlag() {
        return weeklyVisitFlag;
    }

    public void weeklyVisitFlag(boolean weeklyVisitFlag) {
        this.weeklyVisitFlag = weeklyVisitFlag;
    }

    public int health() {
        return health;
    }

    public void health(int health) {
        this.health = health;
    }
}
