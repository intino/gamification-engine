package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Property;

import java.util.*;
import java.util.function.BiFunction;

import static io.intino.gamification.util.time.Crontab.Type.Cyclic;

public class World extends Node {

    private final Property<Match> currentMatch = new Property<>();
    private final SortedSet<Match> finishedMatches = new TreeSet<>(Comparator.comparing(Match::endTime));
    private final EntityCollection<Player> players = new EntityCollection<>();
    private final EntityCollection<Actor> npcs = new EntityCollection<>();
    private final EntityCollection<Item> items = new EntityCollection<>();
    private final SimpleNodeCollection<Mission> missions = new SimpleNodeCollection<>();
    private final SimpleNodeCollection<Achievement> achievements = new SimpleNodeCollection<>();

    public World(String id) {
        super(id);
    }

    //RLP
    public static World create(String id) {
        World world = new World(id);
        GamificationGraph.get().worlds().add(world);
        return world;
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
        players.update();
        npcs.update();
        items.update();
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
        if(match.crontab().type() == Cyclic) {
            Match newMatch = match.copy();
            newMatch.begin();
            return newMatch;
        }
        return null;
    }

    public Match currentMatch() {
        return currentMatch.get();
    }

    //RLP
    public Property<Match> currentMatchProperty() {
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

    public Collection<Match> finishedMatches() {
        return Collections.unmodifiableSet(finishedMatches);
    }

    public SimpleNodeCollection<Mission> missions() {
        return missions;
    }

    public SimpleNodeCollection<Achievement> achievements() {
        return achievements;
    }

    public final class EntityCollection<T extends Entity> extends DeferredNodeCollection<T> {

        public void create(String entityId, BiFunction<String, String, ? extends T> constructor) {
            add(constructor.apply(World.this.id(), entityId));
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
