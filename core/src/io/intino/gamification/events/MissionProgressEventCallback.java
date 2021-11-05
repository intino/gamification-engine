package io.intino.gamification.events;

@FunctionalInterface
public interface MissionProgressEventCallback<T extends MissionProgressEvent> {

    void notify(T event);

    default void castAndNotify(MissionProgressEvent event) {
        notify((T) event);
    }
}
