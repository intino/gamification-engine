package org.example.cinepolis.control.gamification.model.mission;

public class AtencionTickets extends TimePenaltyMission {

    private static final String ID = "AtencionTickets";
    private static final String DESCRIPTION = "Tickets completados";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 1;

    public AtencionTickets() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE, PRIORITY);
    }

    @Override
    protected void setProgressCallbacks() {

    }

    @Override
    protected void initPenaltyMap() {
        penaltyMap.put(7, -5);
        penaltyMap.put(8, -5);

        penaltyMap.put(9, -5);
        penaltyMap.put(10, -5);

        penaltyMap.put(11, -10);
        penaltyMap.put(12, -10);

        penaltyMap.put(13, -5);
        penaltyMap.put(14, -5);

        penaltyMap.put(15, -50);
    }
}
