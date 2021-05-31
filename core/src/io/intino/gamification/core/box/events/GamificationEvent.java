package io.intino.gamification.core.box.events;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public abstract class GamificationEvent extends io.intino.alexandria.event.Event implements java.io.Serializable {

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
        return message.get(parameter).asString();
    }

    protected Integer getAsInt(String parameter) {
        return message.get(parameter).asInteger();
    }

    protected Double getAsDouble(String parameter) {
        return message.get(parameter).asDouble();
    }

    protected <T extends Enum<T>> T getAsEnum(String parameter, Class<T> enumClass) {
        return !message.contains(parameter) ? null : Enum.valueOf(enumClass, message.get(parameter).asString());
    }

    protected Map<String, String> getAsMap(String parameter) {
        return new Gson().fromJson(get(parameter), new TypeToken<Map<String, String>>(){}.getType());
    }

    public GamificationEvent id(String id) {
        if (id == null) this.message.remove("id");
        else this.message.set("id", id);
        return this;
    }

    protected void set(String attribute, String value) {
        this.message.set(attribute, value);
    }

    protected void set(String attribute, Integer value) {
        this.message.set(attribute, value);
    }

    protected void set(String attribute, Double value) {
        this.message.set(attribute, value);
    }

    protected <T extends Enum<T>> void set(String attribute, T value) {
        if (value == null) this.message.remove(attribute);
        else this.message.set(attribute, value.name());
    }

    protected void set(String attribute, Map<String, String> value) {
        set(attribute, new  Gson().toJson(value));
    }

    @Override
    public io.intino.alexandria.message.Message toMessage() {
        return super.toMessage();
    }
}