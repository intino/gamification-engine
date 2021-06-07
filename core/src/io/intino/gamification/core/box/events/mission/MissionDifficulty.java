package io.intino.gamification.core.box.events.mission;

public enum MissionDifficulty {
    Easy(1), Medium(2), Hard(3);

    private final int multiplier;

    MissionDifficulty(int multiplier) {
        this.multiplier = multiplier;
    }

    public int multiplier() {
        return multiplier;
    }
}
