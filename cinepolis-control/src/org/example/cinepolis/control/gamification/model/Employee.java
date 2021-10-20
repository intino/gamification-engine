package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.graph.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Player {

    private final List<Cinema> cinemas;
    private long missionScore;
    private long healthScore;

    public Employee(String worldId, String id) {
        super(worldId, id);
        this.cinemas = new ArrayList<>();
        this.missionScore = 0;
        this.healthScore = 0;
    }

    public List<Cinema> cinemas() {
        return cinemas;
    }

    public long seasonScore() {
        return missionScore + healthScore;
    }

    public long missionScore() {
        return missionScore;
    }

    public long healthScore() {
        return healthScore;
    }

    public void addMissionScore(long score) {
        this.missionScore += score;
    }

    public void missionScore(long missionScore) {
        this.missionScore = missionScore;
    }

    public void healthScore(long healthScore) {
        this.healthScore = healthScore;
    }
}
