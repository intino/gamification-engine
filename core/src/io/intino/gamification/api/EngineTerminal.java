package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.mounter.AchievementMounter;
import io.intino.gamification.core.box.mounter.EntityMounter;
import io.intino.gamification.core.box.mounter.MatchMounter;
import io.intino.gamification.core.box.mounter.MissionMounter;

public class EngineTerminal {

    private final CoreBox box;

    public EngineTerminal(CoreBox box) {
        this.box = box;
    }

    public void feed(ModifyAchievement event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(AchievementNewState event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(Action event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(AttachEntity event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DestroyEntity event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DetachEntity event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(CreateEntity event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(MatchBegin event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(MatchEnd event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(NewMission event) {
        box.mounter(MissionMounter.class).handle(event);
    }

    public void feed(MissionNewState event) {
        box.mounter(MissionMounter.class).handle(event);
    }
}
