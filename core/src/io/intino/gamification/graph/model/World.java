package io.intino.gamification.graph.model;

import io.intino.gamification.data.Property;

import java.util.*;
import java.util.stream.Stream;

public class World extends Node {

    private final Property<Match> currentMatch = new Property<>();
    private final SortedSet<Match> finishedMatches = new TreeSet<>(Comparator.comparing(Match::endTime));
    private final EntityCollection<Player> players = new EntityCollection<>();
    private final EntityCollection<Actor> npcs = new EntityCollection<>();
    private final EntityCollection<Item> items = new EntityCollection<>();

    public World(String id) {
        super(id);
    }

    public void update() {
        runPendingTasks();
        final Match match = currentMatch();
        if(match != null) {
            match.update();
            checkMatchExpiration();
        }
    }

    private void checkMatchExpiration() {
        Match match = currentMatch.get();
        if(match.hasExpired()) {
            handleMatchExpiration(match);
        }
    }

    private void handleMatchExpiration(Match match) {
        match.end();
        finishedMatches.add(match);
        currentMatch.set(startNextMatch(match));
    }

    private Match startNextMatch(Match match) {
        if(match.cycle()) {
            Match newMatch = match.copy();
            newMatch.begin();
            return newMatch;
        }
        return null;
    }

    public Match currentMatch() {
        return currentMatch.get();
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

    public Set<Match> finishedMatches() {
        return Collections.unmodifiableSet(finishedMatches);
    }

    private void runPendingTasks() {
        players.runPendingTasks();
        npcs.runPendingTasks();
        items.runPendingTasks();
    }

    public class EntityCollection<T extends Entity> implements Iterable<T> {

        private static final int INITIAL_CAPACITY = 1024;

        private final List<T> entities = new ArrayList<>(INITIAL_CAPACITY);
        private final Map<String, T> lookupTable = new HashMap<>(INITIAL_CAPACITY);
        private final Queue<T> entitiesToAdd = new ArrayDeque<>();
        private final Queue<T> entitiesToDestroy = new ArrayDeque<>();

        public EntityCollection() {
        }

        public void add(T entity) {
            if(entity == null) return;
            entitiesToAdd.add(entity);
        }

        public void destroy(T entity) {
            if(entity == null || entity.destroyed()) return;
            entity.markDestroyed();
            entitiesToDestroy.add(entity);
        }

        private void runPendingTasks() {
            while(!entitiesToAdd.isEmpty()) {
                addEntity(entitiesToAdd.poll());
            }
            while(!entitiesToDestroy.isEmpty()) {
                destroyEntity(entitiesToDestroy.poll());
            }
        }

        private boolean addEntity(T entity) {
            synchronized (this) {
                if(entity == null || !entity.worldId().equals(id())) return false;
                if(exists(entity)) return false;
                entities.add(entity);
                lookupTable.put(entity.id(), entity);
                entity.onStart();
                return true;
            }
        }

        private void destroyEntity(T entity) {
            synchronized (this) {
                if(entity == null || !entity.worldId().equals(id())) return;
                if(!exists(entity)) return;
                entities.remove(entity);
                lookupTable.remove(entity.id());
                entity.onDestroy();
            }
        }

        public T find(String id) {
            return lookupTable.get(id);
        }

        public boolean exists(T entity) {
            return lookupTable.containsKey(entity.id());
        }

        public Stream<T> stream() {
            return entities.stream();
        }

        public List<T> list() {
            return Collections.unmodifiableList(entities);
        }

        @Override
        public Iterator<T> iterator() {
            return stream().iterator();
        }
    }
}
