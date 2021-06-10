package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.entity.CreateEnemy;
import io.intino.gamification.core.box.events.entity.CreateItem;
import io.intino.gamification.core.box.events.entity.CreateNpc;
import io.intino.gamification.core.box.events.entity.CreatePlayer;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.MatchState;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.graph.stash.Stash;
import io.intino.magritte.framework.Graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.intino.gamification.core.box.events.achievement.AchievementState.Pending;

public class CoreGraph extends io.intino.gamification.core.graph.AbstractGraph {

	public CoreGraph(Graph graph) {
		super(graph);
	}

	public CoreGraph(io.intino.magritte.framework.Graph graph, CoreGraph wrapper) {
		super(graph, wrapper);
	}

	public World world(String id) {
		return worldList(w -> w.id().equals(id)).findFirst().orElse(null);
	}

	public World world(CreateWorld event) {
		return create(Stash.Worlds.name()).world(event.id());
	}

	/* MATCH ------------------------------------------------------------------------------------------------------ */

	public Match match(String id) {
		return matchList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public List<Match> matchesIn(World world) {
		return matchList(m -> m.worldId().equals(world.id())).collect(Collectors.toList());
	}

	public Match match(BeginMatch event, String worldId) {
		return create(Stash.Matches.name()).match(event.id(), worldId, event.ts(), MatchState.Started.name());
	}

	/* ENTITY ------------------------------------------------------------------------------------------------------ */

	public Entity entity(String id) {
		return entityList(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	public Entity entity(List<Entity> entities, String id) {
		return entities.stream().filter(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	/* PLAYER ------------------------------------------------------------------------------------------------------ */

	public Player player(String id) {
		//TODO
		return entityList(e -> e.id().equals(id) && e instanceof Player)
				.map(e -> (Player) e).findFirst().orElse(null);
	}

	public Player player(List<Player> players, String id) {
		return players.stream().filter(p -> p.id().equals(id)).findFirst().orElse(null);
	}

	public Player player(CreatePlayer event, String worldId) {
		return create(Stash.Players.name()).player(event.id(), worldId);
	}

	/* ENEMY ------------------------------------------------------------------------------------------------------ */

	public Enemy enemy(String id) {
		//TODO
		return entityList(e -> e.id().equals(id) && e instanceof Enemy)
				.map(e -> (Enemy) e).findFirst().orElse(null);
	}

	public Enemy enemy(CreateEnemy event, String worldId) {
		return create(Stash.Enemies.name()).enemy(event.id(), worldId);
	}

	/* NPC ------------------------------------------------------------------------------------------------------ */

	public Npc npc(String id) {
		//TODO
		return entityList(e -> e.id().equals(id) && e instanceof Npc)
				.map(e -> (Npc) e).findFirst().orElse(null);
	}

	public Npc npc(CreateNpc event, String worldId) {
		return create(Stash.Npcs.name()).npc(event.id(), worldId);
	}

	/* ITEM ------------------------------------------------------------------------------------------------------ */

	public Item item(String id) {
		//TODO
		return entityList(e -> e.id().equals(id) && e instanceof Item)
				.map(e -> (Item) e).findFirst().orElse(null);
	}

	public Item item(List<Item> items, String id) {
		//TODO
		return items.stream().filter(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	public Item item(CreateItem event, String worldId) {
		return create(Stash.Items.name()).item(event.id(), worldId);
	}

	/* PLAYER STATE ------------------------------------------------------------------------------------------------------ */

	public PlayerState playerState(List<PlayerState> playersState, String playerId) {
		return playersState.stream().filter(ps -> ps.playerId().equals(playerId)).findFirst().orElse(null);
	}

	public PlayerState playerState(String playerId) {
		return create(Stash.PlayersState.name()).playerState(playerId);
	}

	/* MISSION ------------------------------------------------------------------------------------------------------ */

	public Mission mission(String id) {
		return missionList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public Mission mission(List<Mission> missions, String id) {
		return missions.stream().filter(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public Map<Mission, List<Player>> mission(Class<? extends GamificationEvent> clazz) {
		Map<Mission, List<Player>> missionMap = new HashMap<>();

		for (Match match : worldList().stream().map(World::match).filter(Objects::nonNull).collect(Collectors.toList())) {
			for (Mission mission : match.missions()) {
				if(mission.event().equals(EventType.get(clazz))) {
					missionMap.put(mission, match.players());
				}
			}
		}

		return missionMap;
	}

	public Mission mission(NewMission event) {
		return create(Stash.Missions.name()).mission(event.id(), event.difficulty().name(), event.type().name(), event.description(), event.event().clazzName(), event.maxCount());
	}

	/* MISSION STATE ------------------------------------------------------------------------------------------------------ */

	public MissionState missionStateOf(String missionId, String playerId) {
		return missionStateList(ms -> ms.missionId().equals(missionId) && ms.playerId().equals(playerId))
				.findFirst().orElse(null);
	}

	public MissionState missionState(List<MissionState> missionState, String id) {
		return missionState.stream().filter(ms -> ms.missionId().equals(id)).findFirst().orElse(null);
	}

	public List<MissionState> missionState(String id) {
		return missionStateList(ms -> ms.missionId().equals(id)).collect(Collectors.toList());
	}

	public MissionState missionState(NewStateMission event, String matchId, String missionId, String playerId) {
		return create(Stash.MissionsState.name()).missionState(matchId, missionId, playerId, event.state().name());
	}

	public MissionState missionState(String worldId, String missionId, String playerId) {
		return create(Stash.MissionsState.name()).missionState(worldId, missionId, playerId, io.intino.gamification.core.box.events.mission.MissionState.Pending.name());
	}

	/* ACHIEVEMENT ------------------------------------------------------------------------------------------------------ */

	public Achievement achievement(String id) {
		return achievementList(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public Achievement achievement(List<Achievement> achievements, String id) {
		return achievements.stream().filter(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public Map<Achievement, List<Player>> achievement(Class<? extends GamificationEvent> clazz) {
		Map<Achievement, List<Player>> achievementMap = new HashMap<>();

		for (World world : worldList()) {
			List<Achievement> achievements = world.achievements();
			if(world.match() != null) achievements.addAll(world.match().achievements());

			for (Achievement achievement : achievements) {
				if(achievement.event().equals(EventType.get(clazz))) {
					achievementMap.put(achievement, world.players());
				}
			}
		}

		return achievementMap;
	}

	public Achievement achievement(CreateAchievement event) {
		return create(Stash.Achievements.name()).achievement(event.id(), event.description(), event.event().clazzName(), event.maxCount());
	}

	/* ACHIEVEMENT STATE ------------------------------------------------------------------------------------------------------ */

	public AchievementState achievementStateOf(String achievementId, String playerId) {
		return achievementStateList().stream()
				.filter(as -> as.achievementId().equals(achievementId))
				.filter(as -> as.playerId().equals(playerId))
				.findFirst().orElse(null);
	}

	public List<AchievementState> achievementState(String id) {
		return achievementStateList(as -> as.achievementId().equals(id))
				.collect(Collectors.toList());
	}

	public AchievementState achievementState(AchievementNewState event, String achievementId, String playerId) {
		return create(Stash.AchievementsState.name()).achievementState(achievementId, playerId, event.state().name());
	}

	public AchievementState achievementState(String achievementId, String playerId) {
		return create(Stash.AchievementsState.name()).achievementState(achievementId, playerId, Pending.name());
	}
}