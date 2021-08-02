package old.core.box.utils;

public class MathUtils {

    public static double clamp(double value, double min, double max) {
        return Math.min(Math.max(min, value), max);
    }
}
