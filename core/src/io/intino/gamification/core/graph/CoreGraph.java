package io.intino.gamification.core.graph;

import com.google.gson.Gson;
import io.intino.gamification.core.box.events.entity.CreateEntity;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.MatchState;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
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

	/*public Entity getEntity(String id) {
		return entityList(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	public Achievement getAchievement(String id) {
		return achievementList(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public List<AchievementState> getAchievementStates(Achievement achievement) {
		return achievementStateList(a -> a.achievement().equals(achievement)).collect(Collectors.toList());
	}

	public Achievement achievement(CreateAchievement event) {
		return create(Stash.Achievement.name()).achievement(event.id(), event.type().name(), event.description());
	}

	public AchievementState achievementState(AchievementNewState event, Achievement achievement) {
		return create(Stash.AchievementState.name()).achievementState(achievement, event.match(), event.player(), event.state().name());
	}

	public EntityState entityState(String id) {
		return entityStateList(e -> e.player().id().equals(id)).findFirst().orElse(null);
	}

	*/




	public boolean existsWorld(String id) {
		return worldList().stream().anyMatch(w -> w.id().equals(id));
	}

	public World world(String id) {
		return worldList(w -> w.id().equals(id)).findFirst().orElse(null);
	}

	public World world(CreateWorld event) {
		return create(Stash.World.name()).world(event.id(), new ArrayList<>(), null);
	}

	public boolean existsMatch(String id) {
		return matchList().stream().anyMatch(m -> m.id().equals(id));
	}

	public Match match(String id) {
		return matchList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public List<Match> matchesIn(World world) {
		return matchList(m -> m.world().id().equals(world.id())).collect(Collectors.toList());
	}

	public Match match(BeginMatch event, World world) {
		return create(Stash.Match.name()).match(event.id(), world, event.ts(), null, MatchState.Started.name(), new ArrayList<>(), new ArrayList<>());
	}

	public boolean existsMission(String id) {
		return missionList().stream().anyMatch(m -> m.id().equals(id));
	}

	public Mission mission(String id) {
		return missionList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public Mission mission(NewMission event) {
		return create(Stash.Mission.name()).mission(event.id(), event.difficulty().name(), event.type().name(), event.description());
	}

	public MissionState missionState(NewStateMission event, Mission mission) {
		return create(Stash.MissionState.name()).missionState(mission, event.state().name());
	}

	public boolean existsEntity(String id) {
		return entityList().stream().anyMatch(e -> e.id().equals(id));
	}

	public Entity entity(String id) {
		return entityList(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	public Entity entity(CreateEntity event, World world) {
		return create(Stash.Entity.name()).entity(event.id(), event.type().name(), world, new Gson().toJson(event.attributes()), null, new ArrayList<>());
	}

	public EntityState entityState(Entity player) {
		return create(Stash.EntityState.name()).entityState(player, 0, new ArrayList<>());
	}
}