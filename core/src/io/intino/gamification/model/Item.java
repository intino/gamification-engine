package io.intino.gamification.model;

import io.intino.gamification.data.Property;
import io.intino.gamification.data.ReadOnlyProperty;

public class Item extends Entity {

    private final Property<Actor> owner = new Property<>();

    public Item(String world, String id) {
        super(world, id);
    }

    public Actor owner() {
        return owner.get();
    }

    void owner(Actor owner) {
        this.owner.set(owner);
    }

    public ReadOnlyProperty<Actor> ownerProperty() {
        return owner;
    }
}
