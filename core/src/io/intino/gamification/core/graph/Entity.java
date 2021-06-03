package io.intino.gamification.core.graph;

import com.google.gson.Gson;
import io.intino.gamification.core.box.events.entity.EntityType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Entity extends AbstractEntity {

	public static final double MIN_HEALTH = 0.0;
	public static final double MAX_HEALTH = 100.0;

	private static final Map<String, AttributeListener<String>> AttributeListeners = new ConcurrentHashMap<>();

	public static AttributeListener<String> getAttributeListener(String attributeName) {
		return AttributeListeners.get(attributeName);
	}

	public static <T> void setAttributeListener(String attributeName, AttributeListener<T> listener, Function<String, T> mapper) {
		if(listener != null)
			AttributeListeners.put(attributeName, new AttributeListenerWrapper<>(listener, mapper));
		else
			AttributeListeners.remove(attributeName);
	}

	private final Map<String, String> attributesMap;

	@SuppressWarnings("unchecked")
	public Entity(io.intino.magritte.framework.Node node) {
		super(node);
		attributesMap = (Map<String, String>) new Gson().fromJson(attributes(), Map.class);
	}

	public String get(String name) {
		return attributesMap.get(name);
	}

	public <T> T get(String name, Function<String, T> mapper) {
		return mapper.apply(get(name));
	}

	public Entity set(String name, Object value) {
		if(isPrimaryAttribute(name, value)) return this;
		attributesMap.put(name, String.valueOf(value));
		return this;
	}

	private boolean isPrimaryAttribute(String name, Object value) {
		switch(name) {
			case "level":  return level(asInt(value)) != null;
			case "score":  return score(asInt(value)) != null;
			case "health": return health(clamp(asDouble(value), MIN_HEALTH, MAX_HEALTH)) != null;
		}
		return false;
	}

	private double asDouble(Object value) {
		if(value == null) return 0.0;
		return value instanceof Number ? ((Number)value).doubleValue() : Double.parseDouble(String.valueOf(value));
	}

	private int asInt(Object value) {
		if(value == null) return 0;
		return value instanceof Number ? ((Number)value).intValue() : Integer.parseInt(String.valueOf(value));
	}

	@Override
	public void save$() {
		attributes = new Gson().toJson(attributesMap);
		super.save$();
	}

	public EntityType type() {
		return EntityType.valueOf(typeName);
	}

	public Entity type(EntityType type) {
		typeName(type.name());
		return this;
	}

	public interface AttributeListener<T> {
		T onAttributeChange(Entity entity, T oldValue, T newValue);
	}

	private static class AttributeListenerWrapper<T> implements AttributeListener<String> {

		private final AttributeListener<T> listener;
		private final Function<String, T> mapper;

		public AttributeListenerWrapper(AttributeListener<T> listener, Function<String, T> mapper) {
			this.listener = listener;
			this.mapper = mapper;
		}

		@Override
		public String onAttributeChange(Entity entity, String oldValue, String newValue) {
			return String.valueOf(listener.onAttributeChange(entity, mapper.apply(oldValue), mapper.apply(newValue)));
		}
	}

	private double clamp(double value, double min, double max) {
		return Math.min(Math.max(min, value), max);
	}
}