package old.core.model.attributes;

public enum MissionType {

    Primary(2), Secondary(1), Special(3);

    private float multiplier;

    MissionType(float multiplier) {
        this.multiplier = multiplier;
    }

    public float multiplier() {
        return multiplier;
    }

    public void multiplier(float multiplier) {
        this.multiplier = multiplier;
    }
}
