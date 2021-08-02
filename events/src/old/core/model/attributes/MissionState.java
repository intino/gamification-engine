package old.core.model.attributes;

public enum MissionState {

    Completed(2), Cancelled(0), Failed(-1), Pending(0);

    private float multiplier;

    MissionState(float multiplier) {
        this.multiplier = multiplier;
    }

    public float multiplier() {
        return multiplier;
    }

    public void multiplier(float multiplier) {
        this.multiplier = multiplier;
    }
}
