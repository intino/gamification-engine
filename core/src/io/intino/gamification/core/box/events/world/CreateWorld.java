package io.intino.gamification.core.box.events.world;

import io.intino.alexandria.event.Event;
import io.intino.alexandria.message.Message;
import io.intino.gamification.core.box.events.GamificationEvent;

public class CreateWorld extends GamificationEvent {

    public CreateWorld() {
        super("CreateWorld");
    }

    public CreateWorld(Event event) {
        super(event);
    }

    public CreateWorld(Message message) {
        super(message);
    }
}
