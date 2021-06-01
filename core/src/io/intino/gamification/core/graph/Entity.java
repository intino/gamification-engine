package io.intino.gamification.core.graph;

import com.google.gson.Gson;
import io.intino.gamification.core.box.events.enumerates.AchievementType;
import io.intino.gamification.core.box.events.enumerates.EntityType;

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

	@Override
	public void save$() {
		attributes = new Gson().toJson(attributesMap);
		super.save$();
	}

	public EntityType type() {
		return EntityType.valueOf(typeName);
	}

	public Entity type(AchievementType type) {
		typeName(type.name());
		return this;
	}
}