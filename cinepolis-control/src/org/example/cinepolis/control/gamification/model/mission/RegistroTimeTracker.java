package org.example.cinepolis.control.gamification.model.mission;

public class RegistroTimeTracker extends CinesaMission {

    private static final String ID = "RegistroTimeTracker";
    private static final String DESCRIPTION = "Entrada y Salida";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 1;

    private static final int MAX_POINTS = 100;
    private static final int DAILY_PENALTY = 10;

    public RegistroTimeTracker() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE, PRIORITY);
    }

    @Override
    protected void setProgressCallbacks() {

    }

    @Override
    public int maxPoints() {
        return MAX_POINTS;
    }

    @Override
    public int dailyPenalty() {
        return DAILY_PENALTY;
    }
}
