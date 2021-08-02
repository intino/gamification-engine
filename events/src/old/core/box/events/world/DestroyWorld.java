package old.core.box.events.world;

import io.intino.alexandria.event.Event;
import io.intino.alexandria.message.Message;
import old.core.box.events.GamificationEvent;

public class DestroyWorld extends GamificationEvent {

    public DestroyWorld() {
        super(DestroyWorld.class);
    }

    public DestroyWorld(Event event) {
        super(event);
    }

    public DestroyWorld(Message message) {
        super(message);
    }
}
