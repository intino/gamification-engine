package io.intino.gamification.core.box.listeners;

import io.intino.gamification.core.graph.Entity;

public interface EntityAttributeListener<T> {
    T onValueChange(Entity entity, T oldValue, T newValue);
}
