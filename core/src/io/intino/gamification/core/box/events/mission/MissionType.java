package io.intino.gamification.core.box.events.mission;

public enum MissionType {
    Primary(2), Secondary(1), Special(3);

    private final int multiplier;

    MissionType(int multiplier) {
        this.multiplier = multiplier;
    }

    public int multiplier() {
        return multiplier;
    }
}
