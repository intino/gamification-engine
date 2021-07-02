package io.intino.gamification.core.graph;

import io.intino.gamification.api.EngineConfiguration;
import io.intino.gamification.core.box.checkers.entries.MissionEntry;
import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.model.attributes.AchievementType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.entity.CreateItem;
import io.intino.gamification.core.box.events.entity.CreateNpc;
import io.intino.gamification.core.box.events.entity.CreatePlayer;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.model.attributes.MatchState;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.checkers.entries.AchievementEntry;
import io.intino.gamification.core.graph.stash.Stash;
import io.intino.magritte.framework.Graph;

import java.util.*;
import java.util.stream.Collectors;

import static io.intino.gamification.core.model.attributes.AchievementState.Pending;

public class CoreGraph extends io.intino.gamification.core.graph.AbstractGraph {

	private EngineConfiguration engineConfiguration;

	public CoreGraph(Graph graph) {
		super(graph);
	}

	public CoreGraph(io.intino.magritte.framework.Graph graph, CoreGraph wrapper) {
		super(graph, wrapper);
	}

	public EngineConfiguration engineConfig() {
		return engineConfiguration;
	}

	public void engineConfig(EngineConfiguration engineConfiguration) {
		this.engineConfiguration = engineConfiguration;
	}

	/* WORLD ------------------------------------------------------------------------------------------------------ */

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

	public List<Match> activeMatches() {
		return worldList().stream().map(AbstractWorld::match).filter(Objects::nonNull).collect(Collectors.toList());
	}

	public List<Match> matchesIn(World world) {
		return matchList(m -> m.worldId().equals(world.id())).collect(Collectors.toList());
	}

	public Match match(BeginMatch event, String worldId) {
		String id = event.reboot() ? event.id() + "_" + UUID.randomUUID().toString() : event.id();
		return create(Stash.Matches.name()).match(id, worldId, event.ts(), MatchState.Started.name(), event.reboot());
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
		return playerList(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	public Player player(List<Player> players, String id) {
		return players.stream().filter(p -> p.id().equals(id)).findFirst().orElse(null);
	}

	public Player player(CreatePlayer event, String worldId) {
		return create(Stash.Players.name()).player(event.id(), worldId, event.groups() == null ? new ArrayList<>() : event.groups());
	}

	/* NPC ------------------------------------------------------------------------------------------------------ */

	public Npc npc(String id) {
		return npcList(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	public Npc npc(List<Npc> npcs, String id) {
		return npcs.stream().filter(n -> n.id().equals(id)).findFirst().orElse(null);
	}

	public Npc npc(CreateNpc event, String worldId) {
		return create(Stash.Npcs.name()).npc(event.id(), worldId, event.groups() == null ? new ArrayList<>() : event.groups());
	}

	/* ITEM ------------------------------------------------------------------------------------------------------ */

	public Item item(String id) {
		return itemList(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	public Item item(List<Item> items, String id) {
		return items.stream().filter(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	public Item item(CreateItem event, String worldId) {
		return create(Stash.Items.name()).item(event.id(), worldId, event.groups() == null ? new ArrayList<>() : event.groups());
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

	public List<MissionEntry> mission(Class<? extends GamificationEvent> clazz) {
		List<MissionEntry> missionEntries = new ArrayList<>();

		for (World world : worldList(w -> w.match() != null).collect(Collectors.toList())) {
			for (Mission mission : world.match().activeMissions()) {
				if(mission.eventInvolved() == EventType.get(clazz)) {
					missionEntries.add(new MissionEntry(mission, world.id(), world.match().players().stream()
							.filter(p -> mission.players().contains(p.id()))
							.collect(Collectors.toList())));
				}
			}
		}

		return missionEntries;
	}

	public Mission mission(CreateMission event) {
		return create(Stash.Missions.name()).mission(event.id(), event.expiration(), event.players() == null ? new ArrayList<>() : event.players(), event.difficulty().name(), event.type().name(), event.description(), event.eventInvolved().clazzName(), event.maxCount());
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

	public MissionState missionState(NewStateMission event, String worldId, String missionId, String playerId) {
		return create(Stash.MissionsState.name()).missionState(worldId, missionId, playerId, event.state().name());
	}

	public MissionState missionState(String worldId, String missionId, String playerId) {
		return create(Stash.MissionsState.name()).missionState(worldId, missionId, playerId, io.intino.gamification.core.model.attributes.MissionState.Pending.name());
	}

	/* ACHIEVEMENT ------------------------------------------------------------------------------------------------------ */

	public Achievement achievement(String id) {
		return achievementList(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public Achievement achievement(List<Achievement> achievements, String id) {
		return achievements.stream().filter(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public List<AchievementEntry> achievement(Class<? extends GamificationEvent> clazz) {
		List<AchievementEntry> achievementEntries = new ArrayList<>();

		for (World world : worldList()) {
			for (Achievement achievement : world.achievements()) {
				if(achievement.eventInvolved() == EventType.get(clazz)) {
					achievementEntries.add(new AchievementEntry(achievement, world.id(), AchievementType.Global, world.players()));
				}
			}

			if(world.match() != null) {
				for (Achievement achievement : world.match().achievements()) {
					if(achievement.eventInvolved() == EventType.get(clazz)) {
						achievementEntries.add(new AchievementEntry(achievement, world.id(), AchievementType.Local, world.players()));
					}
				}
			}
		}

		return achievementEntries;
	}

	public Achievement achievement(CreateAchievement event) {
		return create(Stash.Achievements.name()).achievement(event.id(), event.description(), event.eventInvolved().clazzName(), event.maxCount());
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