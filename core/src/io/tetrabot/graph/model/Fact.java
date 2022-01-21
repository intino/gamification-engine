package io.tetrabot.graph.model;

import io.tetrabot.util.time.TimeUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;

/**
 * Object that represents facts occurred during a game. It can be pretty much anything and refers to a
 * specific point in time.
 *
 * */
public class Fact implements Serializable {

    private Instant ts;
    private Type type;
    private String name;
    private int points;
    private final Map<String, Object> attributes = new TreeMap<>();

    public Fact() {
        ts = TimeUtils.now();
    }

    public Instant ts() {
        return ts;
    }

    public Fact ts(Instant ts) {
        this.ts = ts;
        return this;
    }

    public Type type() {
        return type;
    }

    public Fact type(Type type) {
        this.type = type;
        return this;
    }

    public String name() {
        return name;
    }

    public Fact name(String name) {
        this.name = name;
        return this;
    }

    public int points() {
        return points;
    }

    public Fact points(int points) {
        this.points = points;
        return this;
    }

    public Map<String, Object> attributes() {
        return attributes;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String attribute) {
        return (T) attributes.get(attribute);
    }

    public Integer getInt(String attribute) {
        return get(attribute);
    }

    public Long getLong(String attribute) {
        return get(attribute);
    }

    public Float getFloat(String attribute) {
        return get(attribute);
    }

    public Double getDouble(String attribute) {
        return get(attribute);
    }

    public String getString(String attribute) {
        return get(attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fact fact = (Fact) o;
        return points == fact.points && Objects.equals(ts, fact.ts) && Objects.equals(type, fact.type) && Objects.equals(name, fact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ts, type, name, points);
    }

    @Override
    public String toString() {
        return "Fact{" +
                "ts=" + ts +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", attributes=" + attributes +
                '}';
    }

    public Fact copy() {
        Fact facts = new Fact().ts(ts).name(name).type(type).points(this.points);
        facts.attributes.putAll(attributes);
        return facts;
    }

    public static class Type {

        private static final Map<String, Type> RegisteredTypes = new HashMap<>();

        public static Type get(String name) {
            return RegisteredTypes.get(name);
        }

        public static synchronized Type register(String name) {
            return RegisteredTypes.computeIfAbsent(name, Type::new);
        }

        public static List<Type> values() {
            return new ArrayList<>(RegisteredTypes.values());
        }

        private final String name;

        private Type(String name) {
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

    public static class StandardTypes {

        public static final Type Reinforcement = Type.register("Reinforcement");
        public static final Type Foul = Type.register("Foul");
        public static final Type Bonus = Type.register("Bonus");
        public static final Type Milestone = Type.register("Bonus");
        public static final Type Prize = Type.register("Prize");
        public static final Type Record = Type.register("Record");
        public static final Type MissionAssigned = Type.register("MissionAssigned");
        public static final Type MissionUpdated = Type.register("MissionUpdated");
        public static final Type MissionComplete = Type.register("MissionComplete");
        public static final Type MissionFailed = Type.register("MissionFailed");

        private static List<Type> values;

        public static List<Type> values() {
            if(values == null) {
                Field[] fields = StandardTypes.class.getFields();
                values = new ArrayList<>(fields.length);
                for(Field field : fields) {
                    try {
                        values.add((Type)field.get(null));
                    } catch (IllegalAccessException ignored) {
                    }
                }
            }
            return values;
        }
    }
}
