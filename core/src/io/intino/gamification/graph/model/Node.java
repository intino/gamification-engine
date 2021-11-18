package io.intino.gamification.graph.model;

import io.intino.gamification.util.Log;
import io.intino.gamification.util.serializer.Json;

import java.io.Serializable;
import java.util.Objects;

public abstract class Node implements Serializable {

    private static final String PARENT_SEPARATOR = "\\$";

    private final String id;
    private String[] parentIds = new String[0];
    private int index = Integer.MIN_VALUE;
    private boolean destroyed = false;

    Node(String id) {
        if(id == null) {
            NullPointerException e = new NullPointerException("Id cannot be null");
            Log.error(e);
            throw e;
        }
        this.id = id;
        initTransientAttributes();
    }

    void buildParents(String parentId) {
        if(parentId == null || parentId.isBlank()) return;
        parentIds = parentId.split(PARENT_SEPARATOR);
    }

    void markAsDestroyed() {
        destroyed = true;
        destroyChildren();
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

    public final boolean isAvailable() {
        return !destroyed;
    }

    private void initTransientAttributes() {}
    void init() {}
    abstract Node parent();
    void destroyChildren() {}

    protected void onCreate() {}
    protected void onDestroy() {}

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
        return Json.toJson(this);
    }

    /*

    public final boolean destroyed() {
        return destroyed;
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initTransientAttributes();
    }

    protected void onUpdate() {}

    */
}
