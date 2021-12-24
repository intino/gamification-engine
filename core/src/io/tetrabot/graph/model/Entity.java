package io.tetrabot.graph.model;

public abstract class Entity extends Node {

    Entity(String id) {
        super(id);
    }

    public final Competition competition() {
        return parent();
    }
}

