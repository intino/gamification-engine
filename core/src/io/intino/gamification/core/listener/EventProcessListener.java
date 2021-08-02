package io.intino.gamification.core.listener;

import io.intino.gamification.model.entity.Entity;

public interface EventProcessListener<S extends Entity> {

    void onProcess(S component);

    default void process(Entity component) {
        onProcess((S) component);
    }
}