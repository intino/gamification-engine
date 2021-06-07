package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.entity.CreateEntity;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.MatchState;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.graph.stash.Stash;
import io.intino.magritte.framework.Graph;

import java.util.List;
import java.util.Map;
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
		return create(Stash.World.name()).world(event.id());
	}

	/* MATCH ------------------------------------------------------------------------------------------------------ */

	public Match match(String id) {
		return matchList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public List<Match> matchesIn(World world) {
		return matchList(m -> m.world().id().equals(world.id())).collect(Collectors.toList());
	}

	public Match match(BeginMatch event, World world) {
		return create(Stash.Match.name()).match(event.id(), world, event.ts(), MatchState.Started.name());
	}

	/* ENTITY ------------------------------------------------------------------------------------------------------ */

	public Entity entity(String id) {
		return entityList(e -> e.id().equals(id)).findFirst().orElse(null);
	}

	/* PLAYER ------------------------------------------------------------------------------------------------------ */

	public Player player(String id) {
		return playerList(p -> p.id().equals(id)).findFirst().orElse(null);
	}

	public Player player(CreateEntity event, World world) {
		return create(Stash.Player.name()).player(event.id(), world);
	}

	/* ENEMY ------------------------------------------------------------------------------------------------------ */

	public Enemy enemy(CreateEntity event, World world) {
		return create(Stash.Enemy.name()).enemy(event.id(), world);
	}

	/* NPC ------------------------------------------------------------------------------------------------------ */

	public Npc npc(CreateEntity event, World world) {
		return create(Stash.Npc.name()).npc(event.id(), world);
	}

	/* ITEM ------------------------------------------------------------------------------------------------------ */

	public Item item(String id) {
		return itemList(i -> i.id().equals(id)).findFirst().orElse(null);
	}

	public Item item(CreateEntity event, World world) {
		return create(Stash.Item.name()).item(event.id(), world);
	}

	/* PLAYER STATE ------------------------------------------------------------------------------------------------------ */

	public PlayerState playerState(List<PlayerState> playersState, String playerId) {
		return playersState.stream().filter(ps -> ps.player().id().equals(playerId)).findFirst().orElse(null);
	}

	public PlayerState playerState(Player player, Match match) {
		return create(Stash.PlayerState.name()).playerState(match, player);
	}

	/* MISSION ------------------------------------------------------------------------------------------------------ */

	public Mission mission(String id) {
		return missionList(m -> m.id().equals(id)).findFirst().orElse(null);
	}

	public Map<Mission, List<Player>> mission(Class<? extends GamificationEvent> clazz) {
		return missionList(m -> m.event().equals(EventType.get(clazz)))
				.collect(Collectors.toMap(m -> m, m -> m.match().players()));
	}

	public Mission mission(NewMission event, Match match) {
		return create(Stash.Mission.name()).mission(event.id(), match, event.difficulty().name(), event.type().name(), event.description(), event.event().clazzName(), event.maxCount());
	}

	/* MISSION STATE ------------------------------------------------------------------------------------------------------ */

	public MissionState missionState(String missionId, String playerId) {
		return missionStateList(ms -> ms.mission().id().equals(missionId) && ms.player().id().equals(playerId))
				.findFirst().orElse(null);
	}

	public MissionState missionState(List<MissionState> missionState, String id) {
		return missionState.stream().filter(ms -> ms.mission().id().equals(id)).findFirst().orElse(null);
	}

	public List<MissionState> missionState(String id) {
		return missionStateList(ms -> ms.mission().id().equals(id)).collect(Collectors.toList());
	}

	public MissionState missionState(NewStateMission event, Mission mission, Player player) {
		return create(Stash.MissionState.name()).missionState(mission, player, event.state().name());
	}

	public MissionState missionState(Mission mission, Player player) {
		return create(Stash.MissionState.name()).missionState(mission, player, io.intino.gamification.core.box.events.mission.MissionState.Pending.name());
	}

    /* ACHIEVEMENT ------------------------------------------------------------------------------------------------------ */

	public Achievement achievement(String id) {
		return achievementList(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public Map<Achievement, List<Player>> achievement(Class<? extends GamificationEvent> clazz) {
		return achievementList(a -> a.event().equals(EventType.get(clazz)))
				.collect(Collectors.toMap(a -> a, a -> a.context().players()));
	}

	public Achievement achievement(CreateAchievement event, Context context) {
		return create(Stash.Achievement.name()).achievement(event.id(), context, event.description(), event.event().clazzName(), event.maxCount());
	}

	/* ACHIEVEMENT STATE ------------------------------------------------------------------------------------------------------ */

	public AchievementState achievementState(String achievementId, String playerId) {
		return achievementStateList().stream()
				.filter(as -> as.achievement().id().equals(achievementId))
				.filter(as -> as.player().id().equals(playerId))
				.findFirst().orElse(null);
	}

	public List<AchievementState> achievementState(String id) {
		return achievementStateList(as -> as.achievement().id().equals(id))
				.collect(Collectors.toList());
	}

	public AchievementState achievementState(AchievementNewState event, Achievement achievement, Player player) {
		return create(Stash.AchievementState.name()).achievementState(achievement, player, event.state().name());
	}

	public AchievementState achievementState(Achievement achievement, Player player) {
		return create(Stash.AchievementState.name()).achievementState(achievement, player, Pending.name());
	}
}