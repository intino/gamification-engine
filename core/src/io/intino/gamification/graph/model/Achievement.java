package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.serializer.Json;

import java.util.Objects;

public final class Achievement extends Node {

    private final Type type;
    private String description;

    public Achievement(String id, Type type) {
        super(id);
        this.type = type;
    }

    public String description() {
        return description;
    }

    public Achievement description(String description) {
        this.description = description;
        return this;
    }

    public Type type() {
        return type;
    }

    public Competition competition() {
        return parent();
    }

    @Override
    public Competition parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Achievement that = (Achievement) o;
        return type == that.type && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, description);
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "type=" + type +
                ", description='" + description + '\'' +
                '}';
    }

    public enum Type {
        Bonus, Milestone, Prize, Record
    }
}
