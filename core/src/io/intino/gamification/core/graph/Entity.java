package io.intino.gamification.core.graph;

import com.google.gson.Gson;

import java.util.Map;
import java.util.function.Function;

public class Entity extends AbstractEntity {

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
}