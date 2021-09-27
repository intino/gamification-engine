package org.example.cinepolis.control.gamification.model.mission;

public class ReportesServicio extends CinesaMission {

    private static final String ID = "ReportesServicio";
    private static final String DESCRIPTION = "Reporte enviado";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 1;

    private static final int MAX_POINTS = 100;
    private static final int DAILY_PENALTY = 10;

    public ReportesServicio() {
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
