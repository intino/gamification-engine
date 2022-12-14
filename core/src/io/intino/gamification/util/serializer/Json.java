package io.intino.gamification.util.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import io.intino.gamification.util.Log;

import java.io.*;

public final class Json {

    public static String toJson(Object obj) {
        return jsonSerializer().toJson(obj);
    }

    public static String toJsonPretty(Object obj) {
        return jsonPrettySerializer().toJson(obj);
    }

    public static <T> T fromJson(Class<T> clazz, String json) {
        return jsonPrettySerializer().fromJson(json, clazz);
    }

    public static <T> T read(Class<T> clazz, File file) {
        try (Reader fileReader = new BufferedReader(new FileReader(file))) {
            return jsonPrettySerializer().fromJson(fileReader, clazz);
        } catch (Exception e) {
            Log.error(e);
        }
        return null;
    }

    @SuppressWarnings("all")
    public static void write(Object obj, File file) {
        try (Writer fileWriter = new BufferedWriter(new FileWriter(file))) {
            jsonPrettySerializer().toJson(obj, obj.getClass(), new JsonWriter(fileWriter));
        } catch (Exception e) {
            Log.error(e);
        }
    }

    private static Gson jsonSerializer() {
        return new Gson();
    }

    private static Gson jsonPrettySerializer() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
