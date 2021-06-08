package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.achievement.*;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.match.*;
import io.intino.gamification.core.box.events.mission.*;
import io.intino.gamification.core.box.events.world.*;

import java.util.Arrays;

public enum EventType {

    AchievementNewState(AchievementNewState.class),
    CreateAchievement(CreateAchievement.class),
    DeleteAchievement(DeleteAchievement.class),
    Action(Action.class),
    CreatePlayer(CreatePlayer.class),
    CreateNpc(CreateNpc.class),
    CreateEnemy(CreateEnemy.class),
    CreateItem(CreateItem.class),
    DestroyEntity(DestroyEntity.class),
    DropItem(DropItem.class),
    PickUpItem(PickUpItem.class),
    EnableEntity(EnableEntity.class),
    DisableEntity(DisableEntity.class),
    BeginMatch(BeginMatch.class),
    EndMatch(EndMatch.class),
    NewMission(NewMission.class),
    NewStateMission(NewStateMission.class),
    CreateWorld(CreateWorld.class),
    DestroyWorld(DestroyWorld.class);

    private final Class<? extends GamificationEvent> clazz;

    EventType(Class<? extends GamificationEvent> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends GamificationEvent> clazz() {
        return clazz;
    }

    public static EventType get(Class<? extends GamificationEvent> clazz) {
        return Arrays.stream(EventType.values()).filter(et -> et.clazz().equals(clazz)).findFirst().orElse(null);
    }

    public String clazzName() {
        return clazz.getCanonicalName();
    }
}
