package io.intino.gamification.events;

import io.intino.gamification.util.data.Progress;

@FunctionalInterface
public interface MissionProgressEventCallback {

    void notify(Progress progress);

    default void castAndNotify(Progress progress) {
        notify(progress);
    }
}
