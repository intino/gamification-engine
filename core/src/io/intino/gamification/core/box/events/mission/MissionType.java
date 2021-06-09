package io.intino.gamification.core.box.events.mission;

public enum MissionType {
    Primary(2), Secondary(1), Special(3);

    private final float multiplier;

    MissionType(float multiplier) {
        this.multiplier = multiplier;
    }

    public float multiplier() {
        return multiplier;
    }
}
