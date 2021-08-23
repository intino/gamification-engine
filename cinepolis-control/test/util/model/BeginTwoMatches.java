package util.model;

import io.intino.gamification.graph.model.Achievement;

public class BeginTwoMatches extends Achievement {

    private static final String ID = "BeginTwoMatches";
    private static final String DESCRIPTION = "Empieza dos partidas";
    private static final int STEPS_TO_COMPLETE = 2;

    public BeginTwoMatches() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE);
    }
}
