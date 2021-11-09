package io.intino.gamification.graph.model;

import io.intino.gamification.events.MissionProgressEvent;
import io.intino.gamification.events.MissionProgressEventCallback;
import io.intino.gamification.events.MissionProgressEventManager;
import io.intino.gamification.graph.GamificationGraph;

import java.util.Objects;

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
    }

    public String description() {
        return description;
    }

    public int priority() {
        return priority;
    }

    @Override
    void initTransientAttributes() {
        setProgressCallbacks();
    }

    @Override
    protected Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0]);
    }

    protected abstract void setProgressCallbacks();

    protected final <T extends MissionProgressEvent> void subscribe(String eventType, MissionProgressEventCallback<T> consumer) {
        MissionProgressEventManager.get().addEventCallback(eventType, consumer);
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
