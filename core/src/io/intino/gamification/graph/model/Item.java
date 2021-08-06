package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Property;
import io.intino.gamification.util.data.ReadOnlyProperty;

public class Item extends Entity {

    private final Property<String> ownerId = new Property<>();

    public Item(String world, String id) {
        super(world, id);
    }

    public String owner() {
        return ownerId.get();
    }

    void owner(String owner) {
        this.ownerId.set(owner);
    }

    public ReadOnlyProperty<String> ownerProperty() {
        return ownerId;
    }
}
