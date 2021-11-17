package io.intino.gamification.graph.model;

import io.intino.gamification.events.MissionProgressEventManager;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.data.Progress;

public abstract class Mission extends Node {

    private final String description;
    private final int priority;

    public Mission(String id, String description) {
        this(id, description, 0);
    }

    public Mission(String id, String description, int priority) {
        super(id);
        this.description = description;
        this.priority = priority;
        MissionProgressEventManager.get().setEventCallback(id, Progress::increment);
    }

    public String description() {
        return description;
    }

    public int priority() {
        return priority;
    }

    public Competition competition() {
        return parent();
    }

    @Override
    public Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0]);
    }

    public void call(String playerId) {
        MissionProgressEventManager.get().callCallback(this, playerId);
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
