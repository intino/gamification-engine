package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.graph.structure.ReadOnlyProperty;

import java.util.function.BiFunction;

@SuppressWarnings("all")
public class World extends Node {

    private final Property<Match> currentMatch = new Property<>();
    private final NodeCollection<Match> finishedMatches = new NodeCollection<>();
    private final EntityCollection<Player> players = new EntityCollection<>();
    private final EntityCollection<Actor> npcs = new EntityCollection<>();
    private final EntityCollection<Item> items = new EntityCollection<>();
    private final NodeCollection<Mission> missions = new NodeCollection<>();
    private final NodeCollection<Achievement> achievements = new NodeCollection<>();

    public World(String id) {
        super(id);
    }

    @Override
    void destroyChildren() {
        final Match match = currentMatch();
        if(match != null && match.isAvailable()) match.markAsDestroyed();

        players.stream().filter(Entity::isAvailable).forEach(p -> p.markAsDestroyed());
        npcs.stream().filter(Entity::isAvailable).forEach(n -> n.markAsDestroyed());
        items.stream().filter(Entity::isAvailable).forEach(i -> i.markAsDestroyed());
    }

    public void startNewMatch(Match match) {

        if(currentMatch.get() == null) {
            currentMatch.set(match);
            if(match != null) match.begin();
        }
    }

    public void finishCurrentMatch() {
        Match match = currentMatch.get();
        if(match != null && match.isAvailable()) {
            match.end();
            finishedMatches.add(match);
            currentMatch.set(null);
        }
    }

    public final Match currentMatch() {
        return currentMatch.get();
    }

    public final ReadOnlyProperty<Match> currentMatchProperty() {
        return currentMatch;
    }

    public final NodeCollection<Match> finishedMatches() {
        return finishedMatches;
    }

    public Match match(String matchId) {
        if(currentMatch() != null && currentMatch().id().equals(matchId)) return currentMatch();
        return finishedMatches.find(matchId);
    }

    public final EntityCollection<Player> players() {
        return players;
    }

    public final EntityCollection<Actor> npcs() {
        return npcs;
    }

    public final EntityCollection<Item> items() {
        return items;
    }

    public final NodeCollection<Mission> missions() {
        return missions;
    }

    public final NodeCollection<Achievement> achievements() {
        return achievements;
    }

    public final class EntityCollection<T extends Entity> extends NodeCollection<T> {

        public <E extends T> E create(String entityId, BiFunction<String, String, E> constructor) {
            final E entity = constructor.apply(World.this.id(), entityId);
            add(entity);
            return entity;
        }

        @Override
        public void add(T node) {
            if(node == null) return;
            super.add(node);
        }

        @Override
        public void destroy(T node) {
            if(node == null || !node.world().id().equals(World.this.id())) return;
            super.destroy(node);
        }
    }
}
