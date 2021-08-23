package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.graph.structure.ReadOnlyProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

import static io.intino.gamification.util.time.Crontab.Type.Cyclic;

@SuppressWarnings("all")
public class World extends Node {

    private final Property<Match> currentMatch = new Property<>();
    private final List<Match> finishedMatches = new ArrayList<>();
    private final EntityCollection<Player> players = new EntityCollection<>();
    private final EntityCollection<Actor> npcs = new EntityCollection<>();
    private final EntityCollection<Item> items = new EntityCollection<>();
    private final SimpleNodeCollection<Mission> missions = new SimpleNodeCollection<>();
    private final SimpleNodeCollection<Achievement> achievements = new SimpleNodeCollection<>();
    private transient AtomicReference<Runnable> matchTask;

    public World(String id) {
        super(id);
        //TODO
        GamificationGraph.get().worlds().add(this);
    }

    @Override
    void preUpdate() {
        saveGraph();
        runMatchTask();
    }

    @Override
    void updateChildren() {
        final Match match = currentMatch();
        if(match != null && match.isAvailable()) match.update();

        players.stream().filter(Entity::isAvailable).forEach(p -> p.update());
        npcs.stream().filter(Entity::isAvailable).forEach(n -> n.update());
        items.stream().filter(Entity::isAvailable).forEach(i -> i.update());
    }

    @Override
    void postUpdate() {
        if(currentMatch.get() != null) checkMatchExpiration();
    }

    @Override
    void initTransientAttributes() {
        matchTask = new AtomicReference<>();
    }

    private void saveGraph() {
        boolean shouldSaveGraph = false;
        {
            shouldSaveGraph |= players.sealContents();
            shouldSaveGraph |= npcs.sealContents();
            shouldSaveGraph |= items.sealContents();
        }
        if(shouldSaveGraph) graph().save();
    }

    private void runMatchTask() {
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
        addPointsToActors(oldMatch);
        currentMatch(nextMatchFrom(oldMatch), true);
    }

    private void addPointsToActors(Match match) {
        match.players().forEach(p -> p.actor().addScore(p.score()));
        match.npcs().forEach(n -> n.actor().addScore(n.score()));
    }

    private Match nextMatchFrom(Match match) {
        return match.crontab().type() == Cyclic ? match.copy() : null;
    }

    //RLP
    private void currentMatch(Match nextMatch, boolean reboot) {

        final Match oldMatch = currentMatch.get();
        if((oldMatch != null && !oldMatch.isAvailable()) || (nextMatch != null && !nextMatch.isAvailable())) return;

        matchTask.set(() -> {
            finish(oldMatch, reboot);
            start(nextMatch, reboot);
        });
    }

    private void finish(Match match, boolean reboot) {
        if(match != null) {
            if(!reboot) match.onDestroy();
            match.end();
            finishedMatches.add(match);
        }
    }

    private void start(Match match, boolean reboot) {
        currentMatch.set(match);
        if(match != null) {
            if(!reboot) match.onCreate();
            match.begin();
        }
    }

    public final Match currentMatch() {
        return currentMatch.get();
    }

    public final void currentMatch(Match nextMatch) {
        currentMatch(nextMatch, false);
    }

    public final ReadOnlyProperty<Match> currentMatchProperty() {
        return currentMatch;
    }

    public final Collection<Match> finishedMatches() {
        return Collections.unmodifiableList(finishedMatches);
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

    public final SimpleNodeCollection<Mission> missions() {
        return missions;
    }

    public final SimpleNodeCollection<Achievement> achievements() {
        return achievements;
    }

    public final class EntityCollection<T extends Entity> extends DeferredNodeCollection<T> {

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
            if(node == null || !node.worldId().equals(World.this.id())) return;
            super.destroy(node);
        }
    }
}
