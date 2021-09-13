package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.serializer.Json;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public abstract class Node implements Serializable {

    private final String id;
    //TODO (REVISAR BIEN DONDE SE USA)
    //private final Property<Boolean> enabled = new Property<>(true);
    //private final Property<Boolean> destroyed = new Property<>(false);

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

    public final boolean isAvailable() {
        //TODO
        return true;
    }

    /*public final boolean enabled() {
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

    public final void destroy() {
        destroyed.set(true);
    }

    public final ReadOnlyProperty<Boolean> destroyedProperty() {
        return this.destroyed;
    }*/

    GamificationGraph graph() {
        return GamificationGraph.get();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initTransientAttributes();
    }

    //RLP
    public final void update() {
        if(isAvailable()) {
            preUpdate();
            onUpdate();
            updateChildren();
            postUpdate();
        }
    }

    //RLP
    void preUpdate() {}
    void updateChildren() {}
    void postUpdate() {}

    //RLP
    void initTransientAttributes() {}

    //RLP
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
