package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.graph.model.Achievement;

public class MonthEmployee extends Achievement {

    private static final String ID = "MonthEmployee";
    private static final String DESCRIPTION = "Empieza dos partidas";
    private static final int STEPS_TO_COMPLETE = 2;

    public MonthEmployee() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE);
    }
}
