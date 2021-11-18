package io.intino.gamification.graph.model;

import io.intino.gamification.events.EventCallback;
import io.intino.gamification.events.EventManager;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.data.Progress;

public abstract class Mission extends Node {

    private final String description;
    private final int priority;

    protected Mission(String id, String description) {
        this(id, description, 0);
    }

    protected Mission(String id, String description, int priority) {
        super(id);
        this.description = description;
        this.priority = priority;
        EventManager.get().setEventCallback(id, (EventCallback<Progress>) Progress::increment);
    }

    public void call(String playerId) {
        EventManager.get().callCallback(this, playerId);
    }

    public final String description() {
        return description;
    }

    public final int priority() {
        return priority;
    }

    public final Competition competition() {
        return parent();
    }

    @Override
    public final Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0]);
    }

    @Override
    public String toString() {
        return "Mission{" +
                "description='" + description + '\'' +
                ", priority=" + priority +
                ", id='" + id() + '\'' +
                '}';
    }
}
