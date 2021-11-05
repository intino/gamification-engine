package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.graph.model.Player;

public class Employee extends Player {

    private long missionScore;
    private long healthScore;

    public Employee(String id) {
        super(id);
        this.missionScore = 0;
        this.healthScore = 0;
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
