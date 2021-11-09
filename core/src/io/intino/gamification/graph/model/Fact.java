package io.intino.gamification.graph.model;

import java.time.Instant;
import java.util.Objects;

public class Fact {

    private final Instant ts;
    private final String type;
    private final String name;
    private final int value;

    public Fact(Instant ts, String type, String name, int value) {
        this.ts = ts;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Instant ts() {
        return ts;
    }

    public String type() {
        return type;
    }

    public String name() {
        return name;
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fact fact = (Fact) o;
        return Objects.equals(ts, fact.ts) && type.equals(fact.type) && Objects.equals(name, fact.name) && Objects.equals(value, fact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ts, type, name, value);
    }

    @Override
    public String toString() {
        return "Fact{" +
                "ts=" + ts +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
