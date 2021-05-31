package io.intino.gamification.core.box.events;

public class MatchBegin extends GamificationEvent {

    public MatchBegin() {
        super("MatchBegin");
    }

    public MatchBegin(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public MatchBegin(io.intino.alexandria.message.Message message) {
        super(message);
    }
}