package io.intino.gamification.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Json {

    private static final Gson JsonSerializer = new Gson();
    private static final Gson JsonPrettySerializer = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object obj) {
        return JsonSerializer.toJson(obj);
    }

    public static String toJsonPretty(Object obj) {
        return JsonPrettySerializer.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return JsonSerializer.fromJson(json, clazz);
    }
}
