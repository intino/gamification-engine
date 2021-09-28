package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.graph.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Player {

    private final List<Cinema> cinemas;

    public Employee(String worldId, String id) {
        super(worldId, id);
        this.cinemas = new ArrayList<>();
    }

    public List<Cinema> cinemas() {
        return cinemas;
    }

    @Override
    protected void onMatchBegin(Match match) {

    }

    @Override
    protected void onMatchEnd(Match match) {

    }

    @Override
    protected void onCreate() {

    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onDestroy() {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
