package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.attributes.MissionStatus;

public class MissionNewStatus extends GamificationEvent {

    public MissionNewStatus() {
        super("MissionNewStatus");
    }

    public MissionNewStatus(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public MissionNewStatus(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public MissionStatus status() {
        return getAsEnum("status", MissionStatus.class);
    }

    public MissionNewStatus status(MissionStatus status) {
        set("status", status);
        return this;
    }
}