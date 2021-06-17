package io.intino.gamification.core.box.events.action;

public class ShiftScore extends Action {

    public ShiftScore() {
        super(ShiftScore.class);
    }

    public ShiftScore(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public ShiftScore(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public Integer shift() {
        return getAsInt("shift");
    }

    public ShiftScore shift(Integer shift) {
        set("shift", shift);
        return this;
    }
}
