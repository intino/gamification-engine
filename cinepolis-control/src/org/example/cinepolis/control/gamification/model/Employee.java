package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.graph.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Player {

    private final List<Cinema> cinemas;
    private long seasonScore;

    public Employee(String worldId, String id) {
        super(worldId, id);
        this.cinemas = new ArrayList<>();
        this.seasonScore = 0;
        scoreProperty().addObserver((oldValue, newValue) -> seasonScore += newValue - oldValue);
    }

    public List<Cinema> cinemas() {
        return cinemas;
    }

    public long seasonScore() {
        return seasonScore;
    }

    public void seasonScore(int seasonScore) {
        this.seasonScore = seasonScore;
    }
}
