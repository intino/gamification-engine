package io.intino.gamification.graph.structure;

import java.time.Instant;
import java.util.Objects;

public class Fact {

    private final Instant ts;
    private final Type type;
    private final String name;
    private final int value;

    public Fact(Instant ts, Type type, String name, int value) {
        this.ts = ts;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Fact(Instant ts, FactType type, String name, int value) {
        this.ts = ts;
        this.type = new Type(type.name());
        this.name = name;
        this.value = value;
    }

    public Instant ts() {
        return ts;
    }

    public Type type() {
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

    public static class Type {

        private final String name;

        public Type(String name) {
            this.name = name;
        }

        public String name() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Type type = (Type) o;
            return Objects.equals(name, type.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum FactType {
        Reinforcement, Foul, Bonus, Mission
    }
}
