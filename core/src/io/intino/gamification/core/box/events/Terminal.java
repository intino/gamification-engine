package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.box.events.action.*;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.events.world.DestroyWorld;
import io.intino.gamification.core.box.mounter.*;

public class Terminal {

    private final CoreBox box;

    public Terminal(CoreBox box) {
        this.box = box;
    }

    public void feed(AchievementNewState event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(CreateAchievement event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(DeleteAchievement event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(Attack event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(DisableEntity event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(EnableEntity event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(Heal event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(SetHealth event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(ShiftScore event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(CreatePlayer event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(CreateNpc event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(CreateItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DestroyPlayer event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DestroyNpc event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DestroyItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(PickUpItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DropItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(BeginMatch event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(EndMatch event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(CreateMission event) {
        box.mounter(MissionMounter.class).handle(event);
    }

    public void feed(NewStateMission event) {
        box.mounter(MissionMounter.class).handle(event);
    }

    public void feed(CreateWorld event) {
        box.mounter(WorldMounter.class).handle(event);
    }

    public void feed(DestroyWorld event) {
        box.mounter(WorldMounter.class).handle(event);
    }
}
