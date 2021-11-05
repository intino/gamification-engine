package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.serializer.Json;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public abstract class Node implements Serializable {

    private final String id;
    private String[] parentIds;
    private final Property<Boolean> enabled = new Property<>(true);
    private final Property<Boolean> destroyed = new Property<>(false);

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

    public final String[] parentIds() {
        return parentIds;
    }

    public final void parent(String parentId) {
        parentIds = parentId.split("\\.");
    }

    public final String absoluteId() {
        return build(parentIds) + "." + id;
    }

    private String build(String[] parentIds) {
        return String.join(".", parentIds);
    }

    public final boolean isAvailable() {
        return enabled.get() && !destroyed.get();
    }

    public final boolean enabled() {
        return enabled.get();
    }

    public final boolean destroyed() {
        return destroyed.get();
    }

    public final void enable() {
        if(enabled.get()) return;
        enabled.set(true);
        onEnable();
    }

    public final void disable() {
        if(!enabled.get()) return;
        enabled.set(false);
        onDisable();
    }

    final void markAsDestroyed() {
        destroyed.set(true);
        destroyChildren();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initTransientAttributes();
    }

    protected abstract Node parent();

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
