package io.intino.gamification.core.box.events.helper;

public class Math {

    public static double asDouble(Object value) {
        if(value == null) return 0.0;
        return value instanceof Number ? ((Number)value).doubleValue() : Double.parseDouble(String.valueOf(value));
    }

    public static int asInt(Object value) {
        if(value == null) return 0;
        return value instanceof Number ? ((Number)value).intValue() : Integer.parseInt(String.valueOf(value));
    }

    public static double clamp(double value, double min, double max) {
        return java.lang.Math.min(java.lang.Math.max(min, value), max);
    }
}
