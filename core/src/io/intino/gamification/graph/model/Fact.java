package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.graph.structure.ReadOnlyProperty;

import java.time.Instant;

public class Fact<T extends Number> {

    private final ReadOnlyProperty<Instant> ts;
    private final ReadOnlyProperty<Type> type;
    private final ReadOnlyProperty<String> name;
    private final ReadOnlyProperty<T> value;

    public Fact(Instant ts, Type type, String name, T value) {
        this.ts = new Property<>(ts);
        this.type = new Property<>(type);
        this.name = new Property<>(name);
        this.value = new Property<>(value);
    }

    public T getValue() {
        return value.get();
    }

    enum Type {
        Success, Fault, Mission, Bonus
    }
}
