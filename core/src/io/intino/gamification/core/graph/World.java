package io.intino.gamification.core.graph;

import java.util.ArrayList;
import java.util.List;

public class World extends AbstractWorld {

	public World(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public List<Entity> entities() {
		List<Entity> entities = new ArrayList<>();
		entities.addAll(players());
		entities.addAll(enemies());
		entities.addAll(npcs());
		entities.addAll(items());
		return entities;
	}
}