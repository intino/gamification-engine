package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.attributes.MissionState;

public class MissionNewState extends GamificationEvent {

    public MissionNewState() {
        super("MissionNewState");
    }

    public MissionNewState(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public MissionNewState(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public MissionState state() {
        return getAsEnum("state", MissionState.class);
    }

    public MissionNewState state(MissionState state) {
        set("state", state);
        return this;
    }
}