package io.intino.gamification.core.box.events.mission;

public enum MissionState {
    Completed(2), Cancelled(0), Failed(-1), Pending(0);

    private final int multiplier;

    MissionState(int multiplier) {
        this.multiplier = multiplier;
    }

    public int multiplier() {
        return multiplier;
    }
}
