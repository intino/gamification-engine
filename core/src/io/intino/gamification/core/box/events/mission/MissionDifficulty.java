package io.intino.gamification.core.box.events.mission;

public enum MissionDifficulty {
    Easy(1), Medium(2), Hard(3);

    private final float multiplier;

    MissionDifficulty(float multiplier) {
        this.multiplier = multiplier;
    }

    public float multiplier() {
        return multiplier;
    }
}
