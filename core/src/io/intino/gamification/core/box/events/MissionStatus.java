package io.intino.gamification.core.box.events;

public class MissionStatus extends GamificationEvent {

    public enum Status {
        Created, Completed, Cancelled
    }

    public MissionStatus() {
        super("MissionStatus");
    }

    public MissionStatus(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public MissionStatus(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public Status status() {
        return getAsEnum("status", Status.class);
    }

    public MissionStatus status(Status status) {
        set("status", status);
        return this;
    }
}