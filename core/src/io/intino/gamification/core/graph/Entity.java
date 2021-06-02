package io.intino.gamification.core.graph;

import com.google.gson.Gson;
import io.intino.gamification.core.box.events.attributes.EntityType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Entity extends AbstractEntity {

	private static final Map<String, AttributeListener<String>> AttributeListeners = new HashMap<>();

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
		attributesMap.put(name, String.valueOf(value));
		return this;
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

		static <T> AttributeListener<T> empty() {
			return (a, b, c) -> {};
		}

		void onAttributeChange(Entity entity, T oldValue, T newValue);
	}

	private static class AttributeListenerWrapper<T> implements AttributeListener<String> {

		private final AttributeListener<T> listener;
		private final Function<String, T> mapper;

		public AttributeListenerWrapper(AttributeListener<T> listener, Function<String, T> mapper) {
			this.listener = listener;
			this.mapper = mapper;
		}

		@Override
		public void onAttributeChange(Entity entity, String oldValue, String newValue) {
			listener.onAttributeChange(entity, mapper.apply(oldValue), mapper.apply(newValue));
		}
	}
}