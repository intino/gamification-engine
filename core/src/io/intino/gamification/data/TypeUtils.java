package io.intino.gamification.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

public final class TypeUtils {

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> type) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T newInstance(Class<T> type, Object... args) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor(classesOf(args));
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class[] classesOf(Object[] objects) {
        Class[] classes = new Class[objects.length];
        for(int i = 0;i < objects.length;i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

    public static void initSingleton(Class<?> clazz) {
        initSingleton(clazz, newInstance(clazz));
    }

    public static void initSingleton(Class<?> clazz, Object value) {
        setAnnotatedField(clazz, Singleton.class, value);
    }

    public static void setAnnotatedField(Class<?> clazz, Class<? extends Annotation> annotation, Object value) {
        Stream.of(clazz.getDeclaredFields())
                .parallel()
                .filter(f -> nonNull(f.getAnnotation(annotation)))
                .findAny()
                .ifPresent(field -> {
                    field.setAccessible(true);
                    try {
                        field.set(null, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAnnotatedField(Class<?> clazz, Class<? extends Annotation> annotation) {
        return (T) Stream.of(clazz.getDeclaredFields())
                .parallel()
                .filter(f -> nonNull(f.getAnnotation(annotation)))
                .findAny()
                .orElse(null);
    }

    public static <T> T singleton(Class<?> clazz) {
        return getAnnotatedField(clazz, Singleton.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T callAnnotatedMethod(Class<?> clazz, Class<? extends Annotation> annotation, Object object) {
        Optional<Method> result = Stream.of(clazz.getDeclaredMethods())
                .parallel()
                .filter(method -> nonNull(method.getDeclaredAnnotation(annotation)))
                .findAny();
        if(result.isPresent()) {
            try {
                Method method = result.get();
                method.setAccessible(true);
                return (T) method.invoke(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T getOrElse(T actual, T orElse) {
        return actual == null ? orElse : actual;
    }
}
