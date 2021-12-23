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

    public static final Gson Serializer = builder(false).create();
    public static final Gson PrettySerializer = builder(false).setPrettyPrinting().create();
    public static final Gson EmbeddedSerializer = builder(true).create();
    public static final Gson EmbeddedPrettySerializer = builder(true).setPrettyPrinting().create();

    private static final Map<Type, List<Field>> FieldsCache = new HashMap<>();

    public static String toJson(Object obj) {
        return Serializer.toJson(obj);
    }

    public static String toJsonPretty(Object obj) {
        return PrettySerializer.toJson(obj);
    }

    public static String toJsonEmbedded(Object obj) {
        return EmbeddedSerializer.toJson(obj);
    }

    public static String toJsonPrettyEmbedded(Object obj) {
        return EmbeddedPrettySerializer.toJson(obj);
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

    public static Gson jsonEmbeddedSerializer() {
        return EmbeddedSerializer;
    }

    public static Gson jsonEmbeddedPrettySerializer() {
        return EmbeddedPrettySerializer;
    }

    private static GsonBuilder builder(boolean embedded) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        addTypeAdapters(builder, embedded);
        return builder;
    }

    private static void addTypeAdapters(GsonBuilder builder, boolean embedded) {

        builder.registerTypeAdapter(Class.class, (JsonSerializer<Class>) (o, type, jsonSerializationContext) -> new JsonPrimitive(o.getName()));
        builder.registerTypeAdapter(Class.class, (JsonDeserializer<Class>) (o, type, jsonSerializationContext) -> {
            try {
                return Class.forName(o.getAsString());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

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

        addNodeTypeAdapter(builder, embedded);
    }

    private static void addNodeTypeAdapter(GsonBuilder builder, boolean embedded) {

        builder.registerTypeHierarchyAdapter(Node.class, (JsonSerializer<Node>) (node, type, jsonSerializationContext) -> {

            JsonObject obj = new JsonObject();

            List<Field> fields = FieldsCache.get(type);

            if(fields == null) {
                fields = TypeUtils.getAllFields(node.getClass());
            }

            for(Field field : fields) {
                try {
                    if(!embedded && (field.getModifiers() & Modifier.TRANSIENT) != 0) continue;
                    obj.add(field.getName(), jsonSerializationContext.serialize(field.get(node)));
                } catch (IllegalAccessException e) {
                    Log.error(e);
                }
            }

            return obj;
        });

        builder.registerTypeHierarchyAdapter(Competition.class, (JsonDeserializer<Object>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject obj = (JsonObject) jsonElement;
            Competition competition = new Competition(obj.get("id").getAsString());
            deserializeNodeContents(competition, type, obj, jsonDeserializationContext, embedded);
            return competition;
        });

        builder.registerTypeHierarchyAdapter(Season.class, (JsonDeserializer<Object>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject obj = (JsonObject) jsonElement;
            Season season = new Season(obj.get("id").getAsString());
            deserializeNodeContents(season, type, obj, jsonDeserializationContext, embedded);
            return season;
        });

        builder.registerTypeHierarchyAdapter(Round.class, (JsonDeserializer<Object>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject obj = (JsonObject) jsonElement;
            Round round = new Round(obj.get("id").getAsString());
            deserializeNodeContents(round, type, obj, jsonDeserializationContext, embedded);
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

    private static void deserializeNodeContents(Node node, Type type, JsonObject obj, JsonDeserializationContext context, boolean embedded) {

        List<Field> fields = FieldsCache.get(type);

        if(fields == null) {
            fields = TypeUtils.getAllFields(node.getClass());
            FieldsCache.put(type, fields);
        }

        for(Field field : fields) {
            field.setAccessible(true);

            if(!embedded && (field.getModifiers() & Modifier.TRANSIENT) != 0) continue;

            String name = field.getName();
            if(!name.equals("id") && obj.has(name)) {
                try {
                    JsonElement jsonElement = obj.get(name);
                    Object value = context.deserialize(jsonElement, field.getType());
                    field.set(node, value);
                    if(field.getType().equals(NodeCollection.class)) {
                        Field owner = NodeCollection.class.getDeclaredField("owner");
                        owner.setAccessible(true);
                        owner.set(value, node);
                        ((NodeCollection<?>)value).initElements();
                    }

                } catch (Exception e) {
                    Log.error(e);
                }
            }
        }
    }
}
