package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Property;
import io.intino.gamification.util.data.ReadOnlyProperty;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

import static io.intino.gamification.util.time.Crontab.Type.Cyclic;

@SuppressWarnings("all")
public class World extends Node {

    public static World create(String id) {
        World world = new World(id);
        GamificationGraph.get().worlds().add(world);
        return world;
    }

    private final Property<Match> currentMatch = new Property<>();
    private final SortedSet<Match> finishedMatches = new TreeSet<>(Comparator.comparing(Match::endTime));
    private final EntityCollection<Player> players = new EntityCollection<>();
    private final EntityCollection<Actor> npcs = new EntityCollection<>();
    private final EntityCollection<Item> items = new EntityCollection<>();
    private final SimpleNodeCollection<Mission> missions = new SimpleNodeCollection<>();
    private final SimpleNodeCollection<Achievement> achievements = new SimpleNodeCollection<>();
    private final AtomicReference<Runnable> matchTask = new AtomicReference<>();

    public World(String id) {
        super(id);
    }

    protected void update() {
        runPendingTasks();
        final Match match = currentMatch();
        if(match != null) {
            match.update();
            checkMatchExpiration();
        }
    }

    private void runPendingTasks() {
        boolean shouldSaveGraph = false;
        {
            shouldSaveGraph |= players.sealContents();
            shouldSaveGraph |= npcs.sealContents();
            shouldSaveGraph |= items.sealContents();
        }
        if(shouldSaveGraph) graph().save();

        Runnable task = matchTask.getAndSet(null);
        if(task != null) task.run();
    }

    private void checkMatchExpiration() {
        Match match = currentMatch.get();
        if(match.hasExpired()) {
            handleMatchExpiration(match);
        }
    }

    private void handleMatchExpiration(Match oldMatch) {
        currentMatch(nextMatch(oldMatch));
    }

    private Match nextMatch(Match match) {
        return match.crontab().type() == Cyclic ? match.copy() : null;
    }

    public Match currentMatch() {
        return currentMatch.get();
    }

    public void currentMatch(Match match) {
        matchTask.set(() -> {
            final Match oldMatch = currentMatch.get();
            if(oldMatch != null) {
                oldMatch.end();
                finishedMatches.add(match);
            }
            currentMatch.set(match);
            if(match != null) match.begin();
        });
    }

    public ReadOnlyProperty<Match> currentMatchProperty() {
        return currentMatch;
    }

    public EntityCollection<Player> players() {
        return players;
    }

    public EntityCollection<Actor> npcs() {
        return npcs;
    }

    public EntityCollection<Item> items() {
        return items;
    }

    public SimpleNodeCollection<Mission> missions() {
        return missions;
    }

    public SimpleNodeCollection<Achievement> achievements() {
        return achievements;
    }

    public Collection<Match> finishedMatches() {
        return Collections.unmodifiableSet(finishedMatches);
    }

    public final class EntityCollection<T extends Entity> extends DeferredNodeCollection<T> {

        public <E extends T> E create(String entityId, BiFunction<String, String, E> constructor) {
            final E entity = constructor.apply(World.this.id(), entityId);
            add(entity);
            return entity;
        }

        @Override
        public void add(T node) {
            if(node == null || !node.worldId().equals(id())) return;
            super.add(node);
        }

        @Override
        public void destroy(T node) {
            if(node == null || !node.worldId().equals(id())) return;
            super.destroy(node);
        }
    }
}
