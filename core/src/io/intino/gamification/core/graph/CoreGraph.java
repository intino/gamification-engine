package io.intino.gamification.core.graph;

import com.google.gson.Gson;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.attributes.MatchState;
import io.intino.gamification.core.box.events.attributes.MissionState;
import io.intino.gamification.core.graph.stash.Stash;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.Layer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	public Achievement getAchievement(String id) {
		return achievementList(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public List<AchievementState> getAchievementStates(String id) {
		return achievementStateList(a -> a.id().equals(id)).collect(Collectors.toList());
	}

	public Match getMatch(String id) {
		return matchList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public Mission getMission(String id) {
		return missionList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public Entity entity(CreateEntity event) {
		return create(Stash.Entity.name()).entity(event.id(), event.type().name(), 1, 0, 100, new Gson().toJson(event.attributes()), null, new ArrayList<>());
	}

	public Achievement achievement(CreateAchievement event) {
		return create(Stash.Achievement.name()).achievement(event.id(), event.type().name(), event.description());
	}

	public AchievementState achievementState(AchievementNewState event) {
		return create(Stash.AchievementState.name()).achievementState(event.id(), event.match(), event.player(), event.state().name());
	}

	public Match match(MatchBegin event) {
		return create(Stash.Match.name()).match(event.id(), event.ts(), null, MatchState.Started.name(), new ArrayList<>());
	}

	public Mission mission(NewMission event) {
		return create(Stash.Mission.name()).mission(event.id(), event.player(), event.difficulty().name(), event.type().name(), event.description(), MissionState.Pending.name());
	}
}