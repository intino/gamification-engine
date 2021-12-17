package io.intino.gamification.util.serializer;

import com.google.gson.*;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.Node;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.TypeUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.List;

public final class Json {

    private static final Gson Serializer = builder().create();
    private static final Gson PrettySerializer = builder().setPrettyPrinting().create();

    public static String toJson(Object obj) {
        return Serializer.toJson(obj);
    }

    public static String toJsonPretty(Object obj) {
        return PrettySerializer.toJson(obj);
    }

    public static <T> T fromJson(Class<T> clazz, String json) {
        return PrettySerializer.fromJson(json, clazz);
    }

    public static <T> T read(Class<T> clazz, File file) {
        try (Reader fileReader = new BufferedReader(new FileReader(file))) {
            return PrettySerializer.fromJson(fileReader, clazz);
        } catch (Exception e) {
            Log.error(e);
        }
        return null;
    }

    @SuppressWarnings("all")
    public static void write(Object obj, File file, boolean pretty) {
        try (Writer fileWriter = new BufferedWriter(new FileWriter(file))) {
            if(pretty)
                PrettySerializer.toJson(obj, fileWriter);
            else
                Serializer.toJson(obj, fileWriter);
        } catch (Exception e) {
            Log.error(e);
        }
    }

    public static Gson jsonSerializer() {
        return Serializer;
    }

    public static Gson jsonPrettySerializer() {
        return PrettySerializer;
    }

    private static GsonBuilder builder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        addTypeAdapters(builder);
        return builder;
    }

    private static void addTypeAdapters(GsonBuilder builder) {

        builder.registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (instant, type, jsonSerializationContext)
                -> new JsonPrimitive(instant.toString().replace(':', '_')));

        builder.registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (jsonElement, type, jsonDeserializationContext) -> {

            if(jsonElement instanceof JsonObject) {
                // Compatibility with older versions
                JsonObject obj = (JsonObject) jsonElement;
                return Instant.ofEpochSecond(obj.get("seconds").getAsLong(), obj.get("nanos").getAsLong());
            }

            return Instant.parse(jsonElement.getAsString().replace('_', ':'));

        });

        builder.registerTypeHierarchyAdapter(Node.class, (JsonSerializer<Node>) (node, type, jsonSerializationContext) -> {

            JsonObject obj = new JsonObject();

            List<Field> fields = TypeUtils.getAllFields(MissionAssignment.class, f -> (f.getModifiers() & Modifier.TRANSIENT) == 0);

            for(Field field : fields) {
                try {
                    obj.add(field.getName(), jsonSerializationContext.serialize(field.get(node)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            return obj;
        });
    }
}
