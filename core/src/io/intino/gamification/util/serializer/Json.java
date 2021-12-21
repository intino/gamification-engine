package io.intino.gamification.util.serializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.intino.gamification.graph.model.*;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.TypeUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Json {

    public static final Gson Serializer = builder().create();
    public static final Gson PrettySerializer = builder().setPrettyPrinting().create();
    private static final Map<Type, List<Field>> FieldsCache = new HashMap<>();

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

        addNodeTypeAdapter(builder);
    }

    private static void addNodeTypeAdapter(GsonBuilder builder) {

        builder.registerTypeHierarchyAdapter(Node.class, (JsonSerializer<Node>) (node, type, jsonSerializationContext) -> {

            JsonObject obj = new JsonObject();

            List<Field> fields = TypeUtils.getAllFields(node.getClass(), field -> (field.getModifiers() & Modifier.TRANSIENT) == 0);

            for(Field field : fields) {
                try {
                    obj.add(field.getName(), jsonSerializationContext.serialize(field.get(node)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            return obj;
        });

        builder.registerTypeHierarchyAdapter(Competition.class, (JsonDeserializer<Object>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject obj = (JsonObject) jsonElement;
            Competition competition = new Competition(obj.get("id").getAsString());
            deserializeNodeContents(competition, type, obj, jsonDeserializationContext);
            return competition;
        });

        builder.registerTypeHierarchyAdapter(Season.class, (JsonDeserializer<Object>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject obj = (JsonObject) jsonElement;
            Season season = new Season(obj.get("id").getAsString());
            deserializeNodeContents(season, type, obj, jsonDeserializationContext);
            return season;
        });

        builder.registerTypeHierarchyAdapter(Round.class, (JsonDeserializer<Object>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject obj = (JsonObject) jsonElement;
            Round round = new Round(obj.get("id").getAsString());
            deserializeNodeContents(round, type, obj, jsonDeserializationContext);
            return round;
        });

        builder.registerTypeAdapter(NodeCollection.class, (JsonSerializer<NodeCollection<?>>) (collection, type, jsonSerializationContext) -> {
            JsonObject obj = new JsonObject();
            obj.add("elementType", new JsonPrimitive(collection.elementType().getName()));
            obj.add("nodes", jsonSerializationContext.serialize(collection.list()));
            return obj;
        });

        builder.registerTypeAdapter(NodeCollection.class, (JsonDeserializer<Object>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject obj = (JsonObject) jsonElement;
            NodeCollection<?> collection = new NodeCollection<>();
            List<Field> fields = FieldsCache.get(NodeCollection.class);
            if(fields == null) {
                fields = TypeUtils.getAllFields(NodeCollection.class, f -> (f.getModifiers() & Modifier.TRANSIENT) == 0);
                FieldsCache.put(NodeCollection.class, fields);
            }

            try {
                Field elementType = fields.stream().filter(f -> f.getName().equals("elementType")).findFirst().orElse(null);
                Field nodes = fields.stream().filter(f -> f.getName().equals("nodes")).findFirst().orElse(null);

                if(elementType == null || nodes == null) throw new RuntimeException("Incompatible versions: elementType or nodes does not exist");

                elementType.setAccessible(true);
                Class<?> elementTypeClass = Class.forName(obj.get("elementType").getAsString());
                elementType.set(collection, elementTypeClass);

                nodes.setAccessible(true);
                Type nodesType = TypeToken.getParameterized(nodes.getType(), elementTypeClass).getType();
                nodes.set(collection, jsonDeserializationContext.deserialize(obj.get("nodes"), nodesType));

            } catch (Exception e) {
                Log.error(e);
            }
            return collection;
         });
    }

    private static void deserializeNodeContents(Node node, Type type, JsonObject obj, JsonDeserializationContext context) {

        List<Field> fields = FieldsCache.get(type);

        if(fields == null) {
            fields = TypeUtils.getAllFields(node.getClass(), field -> (field.getModifiers() & Modifier.TRANSIENT) == 0);
            FieldsCache.put(type, fields);
        }

        for(Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if(!name.equals("id") && obj.has(name)) {
                try {
                    JsonElement jsonElement = obj.get(name);
                    field.set(node, context.deserialize(jsonElement, field.getType()));
                } catch (Exception e) {
                    Log.error(e);
                }
            }
        }
    }
}
