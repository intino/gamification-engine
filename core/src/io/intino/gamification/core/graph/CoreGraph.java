package io.intino.gamification.core.graph;

import com.google.gson.Gson;
import io.intino.gamification.core.box.events.CreateEntity;
import io.intino.magritte.framework.Graph;

import java.util.ArrayList;

public class CoreGraph extends io.intino.gamification.core.graph.AbstractGraph {

	public CoreGraph(Graph graph) {
		super(graph);
	}

	public CoreGraph(io.intino.magritte.framework.Graph graph, CoreGraph wrapper) {
	    super(graph, wrapper);
	}

	public Entity getEntity(String id) {
		return entityList(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	public AchievementDefinition getAchievementDefinition(String id) {
		return achievementDefinitionList(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public io.intino.gamification.core.graph.Entity entity(CreateEntity event) {
		return create("Entity").entity(event.id(), event.type().name(), new Gson().toJson(event.attributes()), null, new ArrayList<>());
	}
}