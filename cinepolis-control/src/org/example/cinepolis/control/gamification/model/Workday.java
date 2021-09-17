package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.graph.model.Match;

public class Workday extends Match {

    public Workday(String worldId, String id) {
        super(worldId, id);
    }

    @Override
    protected void onBegin() {
        world().players().forEach(p -> p.achievementProgress("MonthEmployee").increment());
    }

    @Override
    protected void onEnd() {

    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
