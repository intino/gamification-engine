package io.intino.gamification.graph.model;

import com.google.gson.Gson;
import io.intino.gamification.graph.property.Property;
import io.intino.gamification.graph.property.ReadOnlyProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

//TODO: No hay método enableProperty. O quitar los métodos onEnable/onDisable, o llamarlos desde un observer creado
public abstract class Node implements Serializable {

    private final String id;
    //TODO CONTROLAR ENTIDADES DESACTIVADAS AL TERMINAR UNA MISIÓN/PARTIDA
    private final Property<Boolean> enabled = new Property<>(true);
    private final Property<Boolean> destroyed = new Property<>(false);

    Node(String id) {
        //TODO REGISTRAR ERROR
        if(id == null) throw new NullPointerException("Id cannot be null");
        this.id = id;
        //RLP
        initTransientAttributes();
    }

    public final String id() {
        return id;
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

    GamificationGraph graph() {
        return GamificationGraph.get();
    }

    void markDestroyed() {
        destroyed.set(true);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initTransientAttributes();
    }

    protected void initTransientAttributes() {}

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
        return new Gson().toJson(this);     //TODO: Usar Json
    }
}
