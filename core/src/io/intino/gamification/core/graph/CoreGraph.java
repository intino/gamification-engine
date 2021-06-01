package io.intino.gamification.core.graph;

import com.google.gson.Gson;
import io.intino.gamification.core.box.events.AchievementNewStatus;
import io.intino.gamification.core.box.events.CreateEntity;
import io.intino.gamification.core.box.events.ModifyAchievement;
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

	public Achievement getAchievementDefinition(String id) {
		return achievementList(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public Entity entity(CreateEntity event) {
		return create("Entity").entity(event.id(), event.type().name(), new Gson().toJson(event.attributes()), null, new ArrayList<>());
	}

	public Achievement achivementDefinition(ModifyAchievement event) {
		return create("AchievementDefinition").achievement(event.id(), event.type().name(), event.description());
	}

	public AchievementState achievementChecked(AchievementNewStatus event) {
		return create("AchievementChecked").achievementState(event.id(), event.match(), event.player(), event.status().name());
	}
}