package io.intino.gamification.graph.model;

import io.intino.gamification.events.MissionProgressEventCallback;
import io.intino.gamification.events.MissionProgressEventManager;
import io.intino.gamification.events.MissionProgressEvent;

public abstract class Mission extends CompetitionNode implements Comparable<Mission> {

    private String description;
    private int priority;

    public Mission(String id) {
        super(id);
    }

    public Mission(String id, String description) {
        this(id, description, 0);
    }

    public Mission(String id, String description, int priority) {
        super(id);
        this.description = description;
        this.priority = priority;
    }

    protected final <T extends MissionProgressEvent> void subscribe(String eventType, MissionProgressEventCallback<T> consumer) {
        MissionProgressEventManager.get().addEventCallback(eventType, consumer);
    }

    public final void description(String description) {
        this.description = description;
    }

    public final void priority(int priority) {
        this.priority = priority;
    }

    @Override
    void initTransientAttributes() {
        setProgressCallbacks();
    }

    @Override
    public int compareTo(Mission o) {
        return Integer.compare(priority, o.priority);
    }

    protected abstract void setProgressCallbacks();
}
