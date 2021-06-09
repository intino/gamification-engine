package io.intino.gamification.core.box.events.mission;

public enum MissionState {
    Completed(2), Cancelled(0), Failed(-1), Pending(0);

    private final float multiplier;

    MissionState(float multiplier) {
        this.multiplier = multiplier;
    }

    public float multiplier() {
        return multiplier;
    }
}
