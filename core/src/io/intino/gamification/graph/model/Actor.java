package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.graph.structure.ReadOnlyProperty;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Actor extends Entity {

    private final Property<Long> score = new Property<>(0L);
    private final Inventory inventory = new Inventory();

    public Actor(String worldId, String id) {
        super(worldId, id);
    }

    public final long score() {
        return score.get();
    }

    public final void score(long score) {
        this.score.set(score);
    }

    public final ReadOnlyProperty<Long> scoreProperty() {
        return score;
    }

    public final long totalScore() {
        long score = this.score.get();

        World world = world();
        if(!world.isAvailable()) return score;

        Match match = world.currentMatch();
        if(match == null || !match.isAvailable()) return score;

        return score + (this instanceof Player ? match.player(id()).score() : match.npc(id()).score());
    }

    public final void addScore(long score) {
        this.score.set(this.score() + score);
    }

    public final Inventory inventory() {
        return inventory;
    }

    public enum InventoryPolicy {
        Drop, Destroy
    }

    public final class Inventory implements Iterable<Item>, Serializable {

        private final Set<String> items;
        private final Property<InventoryPolicy> policy = new Property<>(InventoryPolicy.Drop);

        public Inventory() {
            this.items = Collections.synchronizedSet(new LinkedHashSet<>());
        }

        public List<Item> items() {
            return items.stream()
                    .map(i -> world().items().<Item>find(i))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
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
            if(policy() == InventoryPolicy.Drop) dropItems();
            else destroyItems();
        }

        private void destroyItems() {
            items.stream().<Item>map(world().items()::find).filter(Objects::nonNull).forEach(item -> world().items().destroy(item));
        }

        private void dropItems() {
            items.stream().<Item>map(world().items()::find).filter(Objects::nonNull).forEach(item -> item.owner(null));
        }

        public final InventoryPolicy policy() {
            return this.policy.get();
        }

        public final void policy(InventoryPolicy policy) {
            if(policy == null) return;
            this.policy.set(policy);
        }

        public final Property<InventoryPolicy> policyProperty() {
            return this.policy;
        }

        @Override
        public Iterator<Item> iterator() {
            return items.stream().<Item>map(world().items()::find).filter(Objects::nonNull).iterator();
        }
    }
}
