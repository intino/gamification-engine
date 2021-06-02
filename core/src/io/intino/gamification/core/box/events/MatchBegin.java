package io.intino.gamification.core.box.events;

import java.util.List;

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

    public List<String> entities() {
        return getAsList("entities");
    }

    public MatchBegin entities(List<String> entities) {
        set("entities", entities);
        return this;
    }
}