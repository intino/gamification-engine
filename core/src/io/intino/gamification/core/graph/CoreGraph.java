package io.intino.gamification.core.graph;

import com.google.gson.Gson;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.attributes.MatchState;
import io.intino.gamification.core.box.events.attributes.MissionState;
import io.intino.gamification.core.graph.stash.Stash;
import io.intino.magritte.framework.Graph;

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

	public List<AchievementState> getAchievementStates(Achievement achievement) {
		return achievementStateList(a -> a.achievement().equals(achievement)).collect(Collectors.toList());
	}

	public Match getMatch(String id) {
		return matchList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public Mission getMission(String id) {
		return missionList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public World getWorld(String id) {
		return worldList(w -> w.id().equals(id)).findFirst().orElse(null);
	}

	public World getWorld(Match match) {
		return worldList(w -> w.match().id().equals(match.id())).findFirst().orElse(null);
	}

	public Entity entity(CreateEntity event, World world) {
		return create(Stash.Entity.name()).entity(event.id(), event.type().name(), true, world, 1, 0, 100, new Gson().toJson(event.attributes()), null, new ArrayList<>());
	}

	public EntityState entityState(Entity event) {
		return create(Stash.EntityState.name()).entityState(event.id(), 0);
	}

	public Achievement achievement(CreateAchievement event) {
		return create(Stash.Achievement.name()).achievement(event.id(), event.type().name(), event.description());
	}

	public AchievementState achievementState(AchievementNewState event, Achievement achievement) {
		return create(Stash.AchievementState.name()).achievementState(achievement, event.match(), event.player(), event.state().name());
	}

	public Match match(MatchBegin event) {
		return create(Stash.Match.name()).match(event.id(), event.ts(), null, MatchState.Started.name(), new ArrayList<>());
	}

	public Mission mission(NewMission event, Entity player) {
		return create(Stash.Mission.name()).mission(event.id(), player, event.difficulty().name(), event.type().name(), event.description(), MissionState.Pending.name());
	}
}