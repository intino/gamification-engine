package io.intino.gamification.graph.model;

import io.intino.gamification.util.Log;
import io.intino.gamification.util.serializer.Json;

import java.io.Serializable;
import java.util.Objects;

public abstract class Node implements Serializable {

    private final String id;
    private transient Object parent;

    Node(String id) {
        if(id == null || id.isBlank()) {
            IllegalArgumentException e = new IllegalArgumentException("Id cannot be null nor empty");
            Log.error(e);
            throw e;
        }
        this.id = id;
        initTransientAttributes();
    }

    void setParent(Object parent) {
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    public final <T> T parent() {
        return (T) parent;
    }

    public final String id() {
        return id;
    }

    private void initTransientAttributes() {}
    void onInit() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id();
    }

    public String toJson() {
        return Json.toJsonPretty(this);
    }
}
