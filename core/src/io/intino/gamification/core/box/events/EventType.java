package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.achievement.*;
import io.intino.gamification.core.box.events.action.*;
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
    Attack(Attack.class),
    DisableEntity(DisableEntity.class),
    EnableEntity(EnableEntity.class),
    Heal(Heal.class),
    SetHealth(SetHealth.class),
    ChangeScore(ChangeScore.class),
    CreateItem(CreateItem.class),
    CreateNpc(CreateNpc.class),
    CreatePlayer(CreatePlayer.class),
    DestroyEntity(DestroyItem.class),
    DestroyNpc(DestroyNpc.class),
    DestroyPlayer(DestroyPlayer.class),
    DropItem(DropItem.class),
    PickUpItem(PickUpItem.class),
    BeginMatch(BeginMatch.class),
    EndMatch(EndMatch.class),
    CreateMission(CreateMission.class),
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
        return clazz.getSimpleName();
    }
}
