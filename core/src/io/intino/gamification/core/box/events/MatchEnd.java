package io.intino.gamification.core.box.events;

public class MatchEnd extends GamificationEvent {

    public MatchEnd() {
        super("MatchEnd");
    }

    public MatchEnd(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public MatchEnd(io.intino.alexandria.message.Message message) {
        super(message);
    }
}