package io.intino.gamification.core.box.events;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.intino.alexandria.message.Message;

import java.util.List;
import java.util.Map;

public abstract class GamificationEvent extends io.intino.alexandria.event.Event implements java.io.Serializable {

    public GamificationEvent(Class<? extends GamificationEvent> clazz) {
        super(clazz.getCanonicalName());
    }

    public GamificationEvent(String type) {
        super(type);
    }

    public GamificationEvent(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public GamificationEvent(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public GamificationEvent ts(java.time.Instant ts) {
        super.ts(ts);
        return this;
    }

    public GamificationEvent ss(String ss) {
        super.ss(ss);
        return this;
    }

    public String id() {
        return get("id");
    }

    protected String get(String parameter) {
        Message.Value value = message.get(parameter);
        return value != null ? value.asString() : null;
    }

    protected Integer getAsInt(String parameter) {
        Message.Value value = message.get(parameter);
        return value != null ? value.asInteger() : null;
    }

    protected Double getAsDouble(String parameter) {
        Message.Value value = message.get(parameter);
        return value != null ? value.asDouble() : null;
    }

    protected Boolean getAsBoolean(String parameter) {
        Message.Value value = message.get(parameter);
        return value != null ? value.asBoolean() : null;
    }

    protected <T extends Enum<T>> T getAsEnum(String parameter, Class<T> enumClass) {
        return !message.contains(parameter) ? null : Enum.valueOf(enumClass, message.get(parameter).asString());
    }

    protected Map<String, String> getAsMap(String parameter) {
        return new Gson().fromJson(get(parameter), new TypeToken<Map<String, String>>(){}.getType());
    }

    protected List<String> getAsList(String parameter) {
        return new Gson().fromJson(get(parameter), new TypeToken<List<String>>(){}.getType());
    }

    public <T extends GamificationEvent> T id(String id) {
        if (id == null) this.message.remove("id");
        else this.message.set("id", id);
        return (T) this;
    }

    protected void set(String attribute, String value) {
        this.message.set(attribute, value);
    }

    protected void set(String attribute, int value) {
        this.message.set(attribute, value);
    }

    protected void set(String attribute, double value) {
        this.message.set(attribute, value);
    }

    protected void set(String attribute, boolean value) {
        this.message.set(attribute, value);
    }

    protected <T extends Enum<T>> void set(String attribute, T value) {
        if (value == null) this.message.remove(attribute);
        else this.message.set(attribute, value.name());
    }

    protected void set(String attribute, Map<String, String> value) {
        set(attribute, new Gson().toJson(value));
    }

    protected void set(String attribute, List<String> value) {
        set(attribute, new Gson().toJson(value));
    }

    @Override
    public io.intino.alexandria.message.Message toMessage() {
        return super.toMessage();
    }
}