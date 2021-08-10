package io.intino.gamification.graph.model;

import com.google.gson.Gson;
import io.intino.gamification.util.data.Property;
import io.intino.gamification.util.data.ReadOnlyProperty;

import java.io.Serializable;
import java.util.Objects;

public abstract class Node implements Serializable {

    private final String id;
    //TODO CONTROLAR ENTIDADES DESACTIVADAS AL TERMINAR UNA MISIÓN/PARTIDA
    private final Property<Boolean> enabled = new Property<>(true);
    private final Property<Boolean> destroyed = new Property<>(false);

    public Node(String id) {
        //TODO REGISTRAR ERROR
        if(id == null) throw new NullPointerException("Id cannot be null");
        this.id = id;
    }

    public final String id() {
        return id;
    }

    GamificationGraph graph() {
        return GamificationGraph.get();
    }

    public boolean enabled() {
        return enabled.get();
    }

    public boolean destroyed() {
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

    public ReadOnlyProperty<Boolean> destroyedProperty() {
        return this.destroyed;
    }

    void markDestroyed() {
        destroyed.set(true);
    }

    protected void onStart() {}
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
        return new Gson().toJson(this);
    }
}
