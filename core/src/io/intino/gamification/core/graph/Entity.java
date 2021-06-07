package io.intino.gamification.core.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static io.intino.gamification.core.box.helper.Math.*;

public abstract class Entity extends AbstractEntity {

	public static final double MIN_HEALTH = 0.0;
	public static final double MAX_HEALTH = 100.0;

	private static final Map<String, AttributeListener<String>> AttributeListeners = new ConcurrentHashMap<>();

	public static AttributeListener<String> getAttributeListener(String attributeName) {
		return AttributeListeners.getOrDefault(attributeName, AttributeListener.empty());
	}

	public static <T> void setAttributeListener(String attributeName, AttributeListener<T> listener, Function<String, T> mapper) {
		if(listener != null)
			AttributeListeners.put(attributeName, new AttributeListenerWrapper<>(listener, mapper));
		else
			AttributeListeners.remove(attributeName);
	}

	protected Map<String, AttributeHandler> attributesMap = new HashMap<>();

	public Entity(io.intino.magritte.framework.Node node) {
		super(node);
		/*this.attributesMap.put("level", new AttributeHandler() {
			@Override
			public void set(Object value) {
				level(asInt(value));
			}

			@Override
			public String get() {
				return String.valueOf(level());
			}
		});*/

		this.attributesMap.put("health", new AttributeHandler() {
			@Override
			public void set(Object value) {
				health(clamp(asDouble(value), MIN_HEALTH, MAX_HEALTH));
			}

			@Override
			public String get() {
				return String.valueOf(health());
			}
		});
	}

	public String get(String name) {
		return attributesMap.get(name).get();
	}

	public Entity set(String name, Object value) {
		AttributeHandler handler = attributesMap.get(name);
		if(handler != null) handler.set(value);
		return this;
	}

	public interface AttributeListener<T> {

		static <E> AttributeListener<E> empty() {
			return (entity, oldValue, newValue) -> newValue;
		}

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

	protected interface AttributeHandler {
		void set(Object value);
		String get();
	}
}