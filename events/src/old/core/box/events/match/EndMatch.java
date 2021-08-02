package old.core.box.events.match;

import old.core.box.events.GamificationEvent;

public class EndMatch extends GamificationEvent {

    public EndMatch() {
        super(EndMatch.class);
    }

    public EndMatch(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public EndMatch(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public EndMatch world(String world) {
        set("world", world);
        return this;
    }
}