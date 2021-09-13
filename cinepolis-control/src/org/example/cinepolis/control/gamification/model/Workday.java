package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.util.time.Crontab;

public class Workday extends Match {

    public Workday(String worldId, String id) {
        super(worldId, id);
    }

    public Workday(String worldId, String id, Crontab crontab) {
        super(worldId, id, crontab);
    }

    @Override
    protected void onBegin() {
        world().players().forEach(p -> p.achievementProgress("BeginTwoMatches").increment());
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
