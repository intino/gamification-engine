package io.intino.gamification.graph.model;

import io.intino.gamification.graph.model.Match.ActorState;
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
        if(currentMatch.get() != null && currentMatch().isAvailable()) checkMatchExpiration();
    }

    @Override
    void initTransientAttributes() {
        matchTask = new AtomicReference<>();
    }

    @Override
    void destroyChildren() {
        final Match match = currentMatch();
        if(match != null && match.isAvailable()) match.markAsDestroyed();

        players.stream().filter(Entity::isAvailable).forEach(p -> p.markAsDestroyed());
        npcs.stream().filter(Entity::isAvailable).forEach(n -> n.markAsDestroyed());
        items.stream().filter(Entity::isAvailable).forEach(i -> i.markAsDestroyed());
    }

    private void saveGraph() {
        boolean shouldSaveGraph = false;
        {
            shouldSaveGraph |= players.sealContents();
            shouldSaveGraph |= npcs.sealContents();
            shouldSaveGraph |= items.sealContents();
        }
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
        currentMatch(nextMatchFrom(oldMatch));
    }

    private void addPointsToActors(Match match) {
        match.players().forEach(this::addPointsTo);
        match.npcs().forEach(this::addPointsTo);
    }

    private void addPointsTo(ActorState state) {
        Actor actor = state.actor();
        if(actor != null && actor.isAvailable()) {
            actor.addScore(state.score());
        }
    }

    private Match nextMatchFrom(Match match) {
        return match.crontab().type() == Cyclic ? match.copy() : null;
    }

    public void currentMatch(Match nextMatch) {

        final Match oldMatch = currentMatch.get();
        if((oldMatch != null && !oldMatch.isAvailable()) || (nextMatch != null && !nextMatch.isAvailable())) return;

        matchTask.set(() -> {
            finish(oldMatch);
            start(nextMatch);
        });
    }

    private void finish(Match match) {
        if(match != null) {
            match.end();
            finishedMatches.add(match);
        }
    }

    private void start(Match match) {
        currentMatch.set(match);
        if(match != null) {
            match.begin();
        }
    }

    public final Match currentMatch() {
        return currentMatch.get();
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
            if(node == null || !node.world().id().equals(World.this.id())) return;
            super.destroy(node);
        }
    }
}
