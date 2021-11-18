package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.structure.Property;

import java.util.ArrayList;
import java.util.List;

public class Record extends Node {

    private final Policy policy;
    private final String description;
    private final List<String> players = new ArrayList<>();
    private final Property<Float> recordValue = new Property<>();

    Record(String id, Policy policy, String description) {
        super(id);
        this.policy = policy;
        this.description = description;
    }

    public final void updateRecord(String playerId, float newValue) {
        if(playerId == null) return;
        if(recordValue.get() == null) setNewRecord(playerId, newValue);
        if(newValue == recordValue.get()) players.add(playerId);
        if(policy == Policy.GreatestValue && newValue > recordValue.get()) setNewRecord(playerId, newValue);
        if(policy == Policy.SmallestValue && newValue < recordValue.get()) setNewRecord(playerId, newValue);
    }

    private void setNewRecord(String playerId, float newValue) {
        recordValue.set(newValue);

        players.clear();
        players.add(playerId);
    }

    public final Competition competition() {
        return parent();
    }

    @Override
    public final Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0]);
    }

    public enum Policy {
        SmallestValue, GreatestValue
    }
}
