package io.intino.gamification.core.box.events.action;

import io.intino.alexandria.message.Message;
import io.intino.gamification.core.box.events.GamificationEvent;

public abstract class Action extends GamificationEvent {

    public Action(Class<? extends GamificationEvent> clazz) {
        super(clazz);
    }

    public Action(Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public Action world(String world) {
        set("world", world);
        return this;
    }

    public String entityDest() {
        return get("entityDest");
    }

    public Action entityDest(String entityDest) {
        set("entityDest", entityDest);
        return this;
    }

    public String type() {
        return get("type");
    }

    public Action type(String type) {
        set("type", type);
        return this;
    }
}