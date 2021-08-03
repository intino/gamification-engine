package io.intino.gamification.model;

import com.google.gson.Gson;
import io.intino.gamification.data.Property;
import io.intino.gamification.data.ReadOnlyProperty;

import java.io.Serializable;
import java.util.Objects;

public abstract class Node implements Serializable {

    private final String id;
    private final Property<Boolean> destroyed = new Property<>(false);

    public Node(String id) {
        if(id == null) throw new NullPointerException("Id cannot be null");
        this.id = id;
    }

    public final String id() {
        return id;
    }

    public boolean destroyed() {
        return destroyed.get();
    }

    void markDestroyed() {
        destroyed.set(true);
    }

    public ReadOnlyProperty<Boolean> destroyedProperty() {
        return this.destroyed;
    }

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
