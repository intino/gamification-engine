package io.intino.gamification.util.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import io.intino.gamification.util.Log;

import java.io.*;

public final class Json {

    private static final Gson JsonSerializer = new GsonBuilder().create();
    private static final Gson JsonPrettySerializer = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object obj) {
        return JsonSerializer.toJson(obj);
    }

    public static String toJsonPretty(Object obj) {
        return JsonPrettySerializer.toJson(obj);
    }

    public static <T> T fromJson(Class<T> clazz, String json) {
        return JsonPrettySerializer.fromJson(json, clazz);
    }

    public static <T> T read(Class<T> clazz, File file) {
        try (Reader fileReader = new BufferedReader(new FileReader(file))) {
            return JsonPrettySerializer.fromJson(fileReader, clazz);
        } catch (Exception e) {
            Log.error(e);
        }
        return null;
    }

    @SuppressWarnings("all")
    public static void write(Object obj, File file) {
        try (Writer fileWriter = new BufferedWriter(new FileWriter(file))) {
            JsonPrettySerializer.toJson(obj, fileWriter);
        } catch (Exception e) {
            Log.error(e);
        }
    }

    private static Gson jsonSerializer() {
        return JsonSerializer;
    }

    private static Gson jsonPrettySerializer() {
        return JsonPrettySerializer;
    }
}
