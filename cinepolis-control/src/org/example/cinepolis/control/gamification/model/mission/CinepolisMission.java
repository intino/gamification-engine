package org.example.cinepolis.control.gamification.model.mission;

import io.intino.gamification.graph.model.Mission;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
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
    private final Map<Integer, Integer> penalizationMap;

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

    public int penalizationAt(int elapsedHours) {
        return penalizationMap.getOrDefault(elapsedHours, 0);
    }

    public Map<Integer, Integer> penalizationMap() {
        return Collections.unmodifiableMap(penalizationMap);
    }

    private Map<Integer, Integer> buildPenalizationMap() {

        Map<Integer, Integer> map = new TreeMap<>();

        Penalizations p = getClass().getAnnotation(Penalizations.class);
        if(p == null) return map;

        int lastPenalizationPoints = 0;
        for (Penalization penalization : p.value()) {
            map.put(getHoursOf(penalization), Math.abs(penalization.points()) - lastPenalizationPoints);
            lastPenalizationPoints = penalization.points();
        }

        return map;
    }

    private int getHoursOf(Penalization penalization) {
        //TODO: Controlar la hora de finalización de las misiones
        if(penalization.day().equals("*")) {
            return Integer.parseInt(penalization.hour());
        } else {
            return 24 * Integer.parseInt(penalization.day());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", category=" + categoryValue +
                ", frequency=" + frequencyValue +
                ", priority=" + priorityValue +
                ", penalization=" + penalizationMap +
                '}';
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
}
