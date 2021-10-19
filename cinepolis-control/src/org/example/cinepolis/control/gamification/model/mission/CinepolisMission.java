package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.Mission;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public abstract class CinepolisMission extends Mission {

    private static final int MAX_POINTS = 100;

    public static int maxPointsOf(CinepolisMission mission) {
        return Math.round(MAX_POINTS * mission.priorityValue().multiplier);
    }

    private final String name;
    private final CategoryValue categoryValue;
    private final FrequencyValue frequencyValue;
    private final PriorityValue priorityValue;
    private final Map<PenalizationTime, Integer> penalizationMap;

    public CinepolisMission(String id) {
        super(id);
        this.name = find(Name.class);
        this.description(find(Description.class));
        this.categoryValue = find(Category.class);
        this.frequencyValue = find(Frequency.class);
        this.priorityValue = find(Priority.class);
        this.penalizationMap = buildPenalizationMap();
        this.priority(Math.round(100 * priorityValue.multiplier));
    }

    protected <T> T find(Class<? extends Annotation> annotationType) {
        Annotation annotation = getClass().getAnnotation(annotationType);
        if(annotation == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " has not been annotated with " + annotationType.getSimpleName());
        }
        try {
            Method method = annotation.getClass().getMethod("value");
            return (T) method.invoke(annotation);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String name() {
        return name;
    }

    public CategoryValue categoryValue() {
        return categoryValue;
    }

    public FrequencyValue frequencyValue() {
        return frequencyValue;
    }

    public PriorityValue priorityValue() {
        return priorityValue;
    }

    public int penalizationAt(int day, int hour) {
        return penalizationMap.getOrDefault(new PenalizationTime(String.valueOf(day), String.valueOf(hour)), 0);
    }

    public Map<PenalizationTime, Integer> penalizationMap() {
        return Collections.unmodifiableMap(penalizationMap);
    }

    private Map<PenalizationTime, Integer> buildPenalizationMap() {

        Map<PenalizationTime, Integer> map = new TreeMap<>();

        Penalizations p = getClass().getAnnotation(Penalizations.class);
        if(p == null) return map;

        int lastPenalizationPoints = 0;
        for (Penalization penalization : p.value()) {
            map.put(new PenalizationTime(penalization.day(), penalization.hour()), Math.abs(penalization.points()) - lastPenalizationPoints);
            lastPenalizationPoints = penalization.points();
        }

        return map;
    }

    public enum CategoryValue {
        Gestion,
        Actividades,
        Mantenimiento
    }

    public enum FrequencyValue {
        Eventual,
        Diaria,
        Semanal,
        Mensual,
        Trimestral,
        Semestral,
        Anual
    }

    public enum PriorityValue {

        MuyAlta(1),
        Alta(0.75f),
        Media(0.5f),
        Baja(0.25f);

        public final float multiplier;

        PriorityValue(float multiplier) {
            this.multiplier = multiplier;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Name {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Description {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Category {
        CategoryValue value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Frequency {
        FrequencyValue value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Priority {
        PriorityValue value();
    }

    @Repeatable(Penalizations.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Penalization {
        String day() default "*";
        String hour() default "23";
        int points();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Penalizations {
        Penalization[] value();
    }

    public static class PenalizationTime implements Comparable<PenalizationTime> {

        public final String day;
        public final String hour;

        public PenalizationTime(String day, String hour) {
            this.day = day;
            this.hour = hour;
        }

        public boolean matches(int day, int hour) {
            if(!this.day.equals("*") && Integer.parseInt(this.day) != day) return false;
            return this.hour.equals("*") || Integer.parseInt(this.hour) == hour;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PenalizationTime that = (PenalizationTime) o;
            return day == that.day && hour == that.hour;
        }

        @Override
        public int hashCode() {
            return Objects.hash(day, hour);
        }

        @Override
        public int compareTo(PenalizationTime o) {
            return Integer.compare(weightOf(this), weightOf(o));
        }

        private static int weightOf(PenalizationTime pt) {

            int w = 0;

            if(pt.day.equals("*")) {
                w = -100;
            } else {
                w += 100 * Integer.parseInt(pt.day);
            }

            if(pt.hour.equals("*")) {
                w += -1;
            } else {
                w += Integer.parseInt(pt.hour);
            }

            return w;
        }

        @Override
        public String toString() {
            return "{" +
                    "day='" + day + '\'' +
                    ", hour='" + hour + '\'' +
                    '}';
        }
    }
}
