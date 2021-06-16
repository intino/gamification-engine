package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.AchievementState;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.mission.MissionState;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.helper.Time;

import java.time.Instant;

public class EventBuilder {

    public static EnableEntity enableEntity(String worldId, String entityId) {
        EnableEntity enableEntity = new EnableEntity();
        enableEntity.world(worldId);
        enableEntity.id(entityId);
        enableEntity.ts(instant());
        return enableEntity;
    }

    public static Action changeScore(String worldId, String playerId, int score) {
        Action action = Action.changeScore(worldId, playerId, score);
        action.ts(instant());
        return action;
    }

    public static Action setHealth(String worldId, String entityId, String type, double health) {
        Action action = Action.setHealth(worldId, entityId, type, health);
        action.ts(instant());
        return action;
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
        return Time.currentInstant();
    }
}
