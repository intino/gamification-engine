package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.AchievementState;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.box.events.entity.Action;
import io.intino.gamification.core.box.events.entity.DestroyEntity;
import io.intino.gamification.core.box.events.mission.MissionState;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.helper.Time;

import java.time.Instant;

public class EventBuilder {

    public static Action changeScore(String worldId, String playerId, int score) {
        Action action = Action.changeScore(worldId, playerId, score);
        action.ts(instant());
        return action;
    }

    public static DestroyEntity destroyEntity(String worldId, String entityId) {
        DestroyEntity destroyEntity = new DestroyEntity();
        destroyEntity.ts(instant());
        destroyEntity.id(entityId);
        destroyEntity.world(worldId);
        return destroyEntity;
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

    public static AchievementNewState newStateAchievement(String achievementId, String playerId, AchievementState state) {
        AchievementNewState achievementNewState = new AchievementNewState();
        achievementNewState.ts(instant());
        achievementNewState.id(achievementId);
        achievementNewState.player(playerId);
        achievementNewState.state(state);
        return achievementNewState;
    }

    private static Instant instant() {
        return Time.currentInstant();
    }
}
