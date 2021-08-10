package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Property;
import io.intino.gamification.util.data.ReadOnlyProperty;

public class Item extends Entity {

    private final Property<String> ownerId = new Property<>();
    private boolean ownerIsPlayer;

    public Item(String world, String id) {
        super(world, id);
    }

    public String ownerId() {
        return ownerId.get();
    }

    void owner(Actor owner) {
        this.ownerId.set(owner != null ? owner.id() : null);
        this.ownerIsPlayer = owner instanceof Player;
    }

    public ReadOnlyProperty<String> ownerIdProperty() {
        return ownerId;
    }

    @SuppressWarnings("unchecked")
    public <T extends Actor> T owner() {
        if(ownerId.get() == null) return null;
        if(ownerIsPlayer) return (T) world().players().find(ownerId.get());
        return (T) world().npcs().find(ownerId.get());
    }
}
