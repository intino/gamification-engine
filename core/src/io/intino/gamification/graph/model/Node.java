package io.intino.gamification.graph.model;

import io.intino.gamification.util.Log;
import io.intino.gamification.util.serializer.Json;

import java.io.Serializable;
import java.util.Objects;

public abstract class Node implements Serializable {

    private static final String PARENT_SEPARATOR = "$";

    private final String id;
    private transient String[] parentIds = new String[0];
    private transient int index = Integer.MIN_VALUE;

    Node(String id) {
        if(id == null) {
            NullPointerException e = new NullPointerException("Id cannot be null");
            Log.error(e);
            throw e;
        }
        this.id = id;
        initTransientAttributes();
    }

    void setParentIds(String parentId) {
        if(parentId == null || parentId.isBlank()) return;
        parentIds = parentId.split("\\" + PARENT_SEPARATOR);
    }

    public final String id() {
        return id;
    }

    String[] parentIds() {
        return parentIds;
    }

    String absoluteId() {
        if(parentIds == null || parentIds.length == 0) return id;
        return String.join(PARENT_SEPARATOR, parentIds) + PARENT_SEPARATOR + id;
    }

    int index() {
        return index;
    }

    void index(int index) {
        this.index = index;
    }

    private void initTransientAttributes() {}
    void onInit() {}
    abstract Node parent();

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
        return "Node{" +
                "id='" + id + '\'' +
                '}';
    }

    public String toJson() {
        return Json.toJsonPretty(this);
    }
}
