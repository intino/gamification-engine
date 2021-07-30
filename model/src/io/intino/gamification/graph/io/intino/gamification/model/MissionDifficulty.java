package io.intino.gamification.graph.io.intino.gamification.model;

public enum MissionDifficulty {

    Easy(1), Medium(2), Hard(3);

    private float multiplier;

    MissionDifficulty(float multiplier) {
        this.multiplier = multiplier;
    }

    public float multiplier() {
        return multiplier;
    }

    public void multiplier(float multiplier) {
        this.multiplier = multiplier;
    }
}
