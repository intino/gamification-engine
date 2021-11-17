package io.intino.gamification.graph.model;

import io.intino.gamification.util.Log;
import io.intino.gamification.util.serializer.Json;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public abstract class Node implements Serializable {

    public static final String PARENT_SEPARATOR = "\\$";

    private final String id;
    private String[] parentIds = new String[0];
    private boolean enabled = true;
    private boolean destroyed = false;
    int index = Integer.MIN_VALUE;

    Node(String id) {
        if(id == null) {
            NullPointerException e = new NullPointerException("Id cannot be null");
            Log.error(e);
            throw e;
        }
        this.id = id;
        initTransientAttributes();
    }

    public final String id() {
        return id;
    }

    public final String absoluteId() {
        if(parentIds == null || parentIds.length == 0) return id;
        return String.join(PARENT_SEPARATOR, parentIds) + PARENT_SEPARATOR + id;
    }

    public final String[] parentIds() {
        return parentIds;
    }

    public final void buildParents(String parentId) {
        if(parentId == null || parentId.isBlank()) return;
        parentIds = parentId.split(PARENT_SEPARATOR);
    }

    public final boolean isAvailable() {
        return enabled && !destroyed;
    }

    public final boolean enabled() {
        return enabled;
    }

    public final boolean destroyed() {
        return destroyed;
    }

    public final void enable() {
        if(enabled) return;
        enabled = true;
        onEnable();
    }

    public final void disable() {
        if(!enabled) return;
        enabled = false;
        onDisable();
    }

    final void markAsDestroyed() {
        destroyed = true;
        destroyChildren();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initTransientAttributes();
    }

    public abstract Node parent();

    void init() {}
    void destroyChildren() {}
    void initTransientAttributes() {}

    protected void onCreate() {}
    protected void onUpdate() {}
    protected void onDestroy() {}

    protected void onEnable() {}
    protected void onDisable() {}

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
}
