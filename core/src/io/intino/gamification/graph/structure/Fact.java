package io.intino.gamification.graph.structure;

import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.*;

/**
 * Object that represents facts occurred during a game. It can be pretty much anything and refers to a
 * specific point in time.
 *
 * ts, competition, season and round are set automatically when added to a match.
 *
 *
 * */
public class Fact {

    private Instant ts;
    private String competition;
    private String season;
    private String round;
    private Type type;
    private String name;
    private int points;
    private Map<String, Object> attributes;

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

    public String competition() {
        return competition;
    }

    public Fact competition(String competition) {
        this.competition = competition;
        return this;
    }

    public String season() {
        return season;
    }

    public Fact season(String season) {
        this.season = season;
        return this;
    }

    public String round() {
        return round;
    }

    public Fact round(String round) {
        this.round = round;
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
        return points == fact.points && Objects.equals(ts, fact.ts) && Objects.equals(competition, fact.competition) && Objects.equals(season, fact.season) && Objects.equals(round, fact.round) && Objects.equals(type, fact.type) && Objects.equals(name, fact.name) && Objects.equals(attributes, fact.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ts, competition, season, round, type, name, points, attributes);
    }

    @Override
    public String toString() {
        return "Fact{" +
                "ts=" + ts +
                ", competition='" + competition + '\'' +
                ", season='" + season + '\'' +
                ", round='" + round + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", attributes=" + attributes +
                '}';
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
        public static final Type Mission = Type.register("Mission");

        public List<Type> values() {
            return List.of(Mission, Reinforcement, Foul, Bonus, Milestone, Prize, Record);
        }
    }
}
