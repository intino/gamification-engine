package io.intino.gamification.core.graph;

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

	*/

	public boolean existWorld(String id) {
		return worldList().stream().anyMatch(w -> w.id().equals(id));
	}

	public World world(String id) {
		return worldList(w -> w.id().equals(id)).findFirst().orElse(null);
	}

	public World world(CreateWorld event) {
		return create(Stash.World.name()).world(event.id(), new ArrayList<>(), null);
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

	public Mission mission(NewMission event) {
		return create(Stash.Mission.name()).mission(event.id(), event.difficulty().name(), event.type().name(), event.description());
	}

	public MissionState missionState(NewStateMission event, Mission mission) {
		return create(Stash.MissionState.name()).missionState(mission, event.state().name());
	}

	public EntityState entityState(String id) {
		return entityStateList(e -> e.player().id().equals(id)).findFirst().orElse(null);
	}

	public EntityState entityState(NewStateMission event) {
		return create(Stash.EntityState.name())
	}
}