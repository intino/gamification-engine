package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Property;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Actor extends Entity {

    private final Property<Long> score = new Property<>(0L);
    private final Inventory inventory = new Inventory();
    private final Property<InventoryPolicy> inventoryPolicy = new Property<>(InventoryPolicy.Drop);

    public Actor(String world, String id) {
        super(world, id);
    }

    public long score() {
        return score.get();
    }

    public void score(long score) {
        this.score.set(score);
    }

    public Property<Long> scoreProperty() {
        return score;
    }

    public InventoryPolicy inventoryPolicy() {
        return this.inventoryPolicy.get();
    }

    public void inventoryPolicy(InventoryPolicy policy) {
        if(policy == null) return;
        this.inventoryPolicy.set(policy);
    }

    public Property<InventoryPolicy> inventoryPolicyProperty() {
        return this.inventoryPolicy;
    }

    public Inventory inventory() {
        return inventory;
    }

    public enum InventoryPolicy {
        Drop, Destroy
    }

    public final class Inventory {

        private final Set<String> items;

        public Inventory() {
            this.items = new LinkedHashSet<>();
        }

        public boolean add(String itemId) {
            return add(world().items().find(itemId));
        }

        public boolean add(Item item) {
            if(item == null) return false;
            if(item.world() != world()) return false;
            final boolean added = items.add(item.id());
            if(added) item.owner(Actor.this);
            return added;
        }

        public boolean remove(String itemId) {
            return remove(world().items().find(itemId));
        }

        public boolean remove(Item item) {
            if(item == null) return false;
            if(item.world() != world()) return false;
            final boolean removed = items.remove(item.id());
            if(removed) item.owner(null);
            return removed;
        }

        private void destroy() {
            if(inventoryPolicy() == InventoryPolicy.Drop) dropItems();
            else destroyItems();
        }

        private void destroyItems() {
            items.stream().<Item>map(world().items()::find).filter(Objects::nonNull).forEach(item -> item.owner(null));
        }

        private void dropItems() {
            items.stream().<Item>map(world().items()::find).filter(Objects::nonNull).forEach(item -> item.owner(null));
        }
    }
}
