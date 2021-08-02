package old.core.box.events.action;

import io.intino.alexandria.message.Message;
import old.core.box.events.GamificationEvent;

public abstract class AbstractAction extends GamificationEvent {

    public AbstractAction(Class<? extends GamificationEvent> clazz) {
        super(clazz);
    }

    public AbstractAction(Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public AbstractAction world(String world) {
        set("world", world);
        return this;
    }

    public String entityDest() {
        return get("entityDest");
    }

    public AbstractAction entityDest(String entityDest) {
        set("entityDest", entityDest);
        return this;
    }

    public String type() {
        return get("type");
    }

    public AbstractAction type(String type) {
        set("type", type);
        return this;
    }
}