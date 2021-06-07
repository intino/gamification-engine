package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.achievement.*;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.match.*;
import io.intino.gamification.core.box.events.mission.*;
import io.intino.gamification.core.box.events.world.*;
import io.intino.gamification.core.box.mounter.*;

public class EngineTerminal {

    private final CoreBox box;

    public EngineTerminal(CoreBox box) {
        this.box = box;
    }

    public void feed(CreateWorld event) {
        box.mounter(WorldMounter.class).mount(event);
    }

    public void feed(DestroyWorld event) {
        box.mounter(WorldMounter.class).mount(event);
    }

    public void feed(BeginMatch event) {
        box.mounter(MatchMounter.class).mount(event);
    }

    public void feed(EndMatch event) {
        box.mounter(MatchMounter.class).mount(event);
    }

    public void feed(NewMission event) {
        box.mounter(MissionMounter.class).mount(event);
    }

    public void feed(NewStateMission event) {
        box.mounter(MissionMounter.class).mount(event);
    }

    public void feed(CreatePlayer event) {
        box.mounter(EntityMounter.class).mount(event);
    }

    public void feed(CreateEnemy event) {
        box.mounter(EntityMounter.class).mount(event);
    }

    public void feed(CreateNpc event) {
        box.mounter(EntityMounter.class).mount(event);
    }

    public void feed(CreateItem event) {
        box.mounter(EntityMounter.class).mount(event);
    }

    public void feed(DestroyEntity event) {
        box.mounter(EntityMounter.class).mount(event);
    }

    public void feed(PickUpItem event) {
        box.mounter(EntityMounter.class).mount(event);
    }

    public void feed(DropItem event) {
        box.mounter(EntityMounter.class).mount(event);
    }

    public void feed(Action event) {
        box.mounter(EntityMounter.class).mount(event);
    }

    public void feed(CreateAchievement event) {
        box.mounter(AchievementMounter.class).mount(event);
    }

    public void feed(DeleteAchievement event) {
        box.mounter(AchievementMounter.class).mount(event);
    }

    public void feed(AchievementNewState event) {
        box.mounter(AchievementMounter.class).mount(event);
    }
}
