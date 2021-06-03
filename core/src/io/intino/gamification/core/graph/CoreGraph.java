package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.entity.CreateEntity;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.MatchState;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.graph.stash.Stash;
import io.intino.magritte.framework.Graph;

import java.util.List;
import java.util.stream.Collectors;

public class CoreGraph extends io.intino.gamification.core.graph.AbstractGraph {

	public CoreGraph(Graph graph) {
		super(graph);
	}

	public CoreGraph(io.intino.magritte.framework.Graph graph, CoreGraph wrapper) {
	    super(graph, wrapper);
	}

	public boolean existsWorld(String id) {
		return worldList().stream().anyMatch(w -> w.id().equals(id));
	}

	public World world(String id) {
		return worldList(w -> w.id().equals(id)).findFirst().orElse(null);
	}

	//Get por evento

	//Get list

	public World world(CreateWorld event) {
		return create(Stash.World.name()).world(event.id());
	}

	/* MATCH ------------------------------------------------------------------------------------------------------ */

	public boolean existsMatch(String id) {
		return matchList().stream().anyMatch(m -> m.id().equals(id));
	}

	public Match match(String id) {
		return matchList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	//Get por evento

	public List<Match> matchesIn(World world) {
		return matchList(m -> m.world().id().equals(world.id())).collect(Collectors.toList());
	}

	public Match match(BeginMatch event, World world) {
		return create(Stash.Match.name()).match(event.id(), world, event.ts(), MatchState.Started.name());
	}

	/* ENTITY ------------------------------------------------------------------------------------------------------ */

	public boolean existsEntity(String id) {
		return entityList().stream().anyMatch(e -> e.id().equals(id));
	}

	public Entity entity(String id) {
		return entityList(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	//Get por evento

	//Get list

	/* PLAYER ------------------------------------------------------------------------------------------------------ */

	//Exists

	public Player player(String id) {
		return playerList(p -> p.id().equals(id)).findFirst().orElse(null);
	}

	//Get por evento

	//Get list

	public Player player(CreateEntity event, World world) {
		return create(Stash.Player.name()).player(event.id(), world);
	}

	/* ENEMY ------------------------------------------------------------------------------------------------------ */

	//Exists

	//Get por id

	//Get por evento

	//Get list

	public Enemy enemy(CreateEntity event, World world) {
		return create(Stash.Enemy.name()).enemy(event.id(), world);
	}

	/* NPC ------------------------------------------------------------------------------------------------------ */

	//Exists

	//Get por id

	//Get por evento

	//Get list

	public Npc npc(CreateEntity event, World world) {
		return create(Stash.Npc.name()).npc(event.id(), world);
	}

	/* ITEM ------------------------------------------------------------------------------------------------------ */

	//Exists

	public Item item(String id) {
		return itemList(i -> i.id().equals(id)).findFirst().orElse(null);
	}

	//Get por evento

	//Get list

	public Item item(CreateEntity event, World world) {
		return create(Stash.Item.name()).item(event.id(), world);
	}

	/* PLAYER STATE ------------------------------------------------------------------------------------------------------ */

	//Exists

	public PlayerState playerState(List<PlayerState> playersState, String id) {
		return playersState.stream().filter(ps -> ps.player().id().equals(id)).findFirst().orElse(null);
	}

	//Get por evento

	//Get list

	public PlayerState playerState(Player player, Match match) {
		return create(Stash.PlayerState.name()).playerState(match, player);
	}

	/* MISSION ------------------------------------------------------------------------------------------------------ */

	public boolean existsMission(String id) {
		return missionList().stream().anyMatch(m -> m.id().equals(id));
	}

	public Mission mission(String id) {
		return missionList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	//Get evento

	//Get lista

	public Mission mission(NewMission event) {
		return create(Stash.Mission.name()).mission(event.id(), event.difficulty().name(), event.type().name(), event.description());
	}

	/* MISSION STATE ------------------------------------------------------------------------------------------------------ */

	//Exists

	public MissionState missionState(List<MissionState> missionState, String id) {
		return missionState.stream().filter(ms -> ms.mission().id().equals(id)).findFirst().orElse(null);
	}

	//Get por evento

	public List<MissionState> missionStateOf(Mission mission) {
		return missionStateList(ms -> ms.mission().id().equals(mission.id())).collect(Collectors.toList());
	}

	public MissionState missionState(NewStateMission event, Mission mission) {
		return create(Stash.MissionState.name()).missionState(mission, event.state().name());
	}

	/* ACHIEVEMENT ------------------------------------------------------------------------------------------------------ */

	//Exists

	//Get por id

	//Get por evento

	//Get list

	//Create
}