package old.core.box.listeners;

import old.core.graph.Entity;

public interface EntityAttributeListener<T> {
    T onValueChange(Entity entity, T oldValue, T newValue);
}
