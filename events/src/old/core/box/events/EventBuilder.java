package old.core.box.events;

import old.core.box.events.achievement.AchievementNewState;
import old.core.box.events.achievement.CreateAchievement;
import old.core.graph.Achievement;
import old.core.model.attributes.AchievementState;
import old.core.model.attributes.AchievementType;
import old.core.box.events.achievement.DeleteAchievement;
import old.core.box.events.action.ChangeScore;
import old.core.box.events.entity.DestroyItem;
import old.core.box.events.entity.DestroyNpc;
import old.core.box.events.entity.DestroyPlayer;
import old.core.box.events.entity.PickUpItem;
import old.core.box.events.match.BeginMatch;
import old.core.box.events.match.EndMatch;
import old.core.model.attributes.MissionState;
import old.core.box.events.mission.NewStateMission;
import old.core.box.utils.TimeUtils;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EventBuilder {

    public static BeginMatch beginMatch(String worldId, String matchId, Instant from, Instant to) {
        BeginMatch beginMatch = new BeginMatch();
        beginMatch.ts(to);
        beginMatch.id(matchId);
        beginMatch.world(worldId);
        beginMatch.expiration(expirationOf(from, to));
        beginMatch.reboot(true);
        return beginMatch;
    }

    public static EndMatch endMatch(String worldId, String matchId) {
        EndMatch endMatch = new EndMatch();
        endMatch.ts(instant());
        endMatch.id(matchId);
        endMatch.world(worldId);
        return endMatch;
    }

    public static ChangeScore changeScore(String worldId, String playerId, int score) {
        ChangeScore changeScore = new ChangeScore();
        changeScore.ts(instant());
        changeScore.id(UUID.randomUUID().toString());
        changeScore.world(worldId);
        changeScore.entityDest(playerId);
        changeScore.change(score);
        return changeScore;
    }

    public static DestroyPlayer destroyPlayer(String worldId, String playerId) {
        DestroyPlayer destroyPlayer = new DestroyPlayer();
        destroyPlayer.ts(instant());
        destroyPlayer.id(playerId);
        destroyPlayer.world(worldId);
        return destroyPlayer;
    }

    public static DestroyNpc destroyNpc(String worldId, String npcId) {
        DestroyNpc destroyNpc = new DestroyNpc();
        destroyNpc.ts(instant());
        destroyNpc.id(npcId);
        destroyNpc.world(worldId);
        return destroyNpc;
    }

    public static DestroyItem destroyItem(String worldId, String itemId) {
        DestroyItem destroyItem = new DestroyItem();
        destroyItem.ts(instant());
        destroyItem.id(itemId);
        destroyItem.world(worldId);
        return destroyItem;
    }

    public static PickUpItem pickUpItem(String worldId, String itemId, String playerId) {
        PickUpItem pickUpItem = new PickUpItem();
        pickUpItem.world(worldId);
        pickUpItem.player(playerId);
        pickUpItem.id(itemId);
        pickUpItem.ts(instant());
        return pickUpItem;
    }

    public static NewStateMission newStateMission(String worldId, String missionId, String playerId, MissionState state) {
        NewStateMission newStateMission = new NewStateMission();
        newStateMission.ts(instant());
        newStateMission.id(missionId);
        newStateMission.world(worldId);
        newStateMission.player(playerId);
        newStateMission.state(state);
        return newStateMission;
    }

    public static DeleteAchievement deleteAchievement(String achievementId) {
        DeleteAchievement deleteAchievement = new DeleteAchievement();
        deleteAchievement.ts(instant());
        deleteAchievement.id(achievementId);
        return deleteAchievement;
    }

    public static AchievementNewState newStateAchievement(String achievementId, String contextId, String playerId, AchievementState state, AchievementType type) {
        AchievementNewState achievementNewState = new AchievementNewState();
        achievementNewState.ts(instant());
        achievementNewState.id(achievementId);
        achievementNewState.world(contextId);
        achievementNewState.player(playerId);
        achievementNewState.state(state);
        achievementNewState.type(type);
        return achievementNewState;
    }

    private static Instant instant() {
        return TimeUtils.currentInstant();
    }

    private static Instant expirationOf(Instant from, Instant to) {
        return Instant.ofEpochMilli(to.toEpochMilli() + TimeUtils.getInstantDiff(from, to, TimeUnit.MILLISECONDS));
    }

    public static CreateAchievement createAchievement(Achievement achievement, String worldId) {
        CreateAchievement createAchievement = new CreateAchievement();
        createAchievement.id(achievementId(achievement.id()));
        createAchievement.ts(instant());
        createAchievement.world(worldId);
        createAchievement.eventInvolved(achievement.eventInvolved());
        createAchievement.maxCount(achievement.maxCount());
        createAchievement.type(AchievementType.Local);
        createAchievement.description(achievement.description());
        return createAchievement;
    }

    private static String achievementId(String id) {
        String originalId = id.contains("_") ? id.substring(0, id.indexOf('_')) : id;
        return originalId + "_" + UUID.randomUUID().toString();
    }
}