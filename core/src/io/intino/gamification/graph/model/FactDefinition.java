package io.intino.gamification.graph.model;

import io.intino.gamification.events.EventCallback;
import io.intino.gamification.events.EventManager;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.data.Progress;

import java.util.Objects;

public abstract class FactDefinition extends Node {

    protected String description;
    protected final int points;

    FactDefinition(String id, int points) {
        super(id);
        this.points = points;
        EventManager.get().setEventCallback(id, (EventCallback<Round.Match>) this::addFactTo);
    }

    public void call(String playerId) {
        EventManager.get().callCallback(this, playerId);
    }

    protected abstract void addFactTo(Round.Match match);

    public final Competition competition() {
        return parent();
    }

    @Override
    public final Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get().competitions().find(ids[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FactDefinition that = (FactDefinition) o;
        return points == that.points && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, points);
    }
}
