package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.AchievementState;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.box.events.action.ChangeScore;
import io.intino.gamification.core.box.events.entity.DestroyItem;
import io.intino.gamification.core.box.events.entity.DestroyNpc;
import io.intino.gamification.core.box.events.entity.DestroyPlayer;
import io.intino.gamification.core.box.events.entity.PickUpItem;
import io.intino.gamification.core.box.events.mission.MissionState;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.utils.TimeUtils;

import java.time.Instant;
import java.util.UUID;

public class EventBuilder {

    public static ChangeScore shiftScore(String worldId, String playerId, int score) {
        ChangeScore shiftScore = new ChangeScore();
        shiftScore.ts(instant());
        shiftScore.id(UUID.randomUUID().toString());
        shiftScore.world(worldId);
        shiftScore.entityDest(playerId);
        shiftScore.change(score);
        return shiftScore;
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
}
