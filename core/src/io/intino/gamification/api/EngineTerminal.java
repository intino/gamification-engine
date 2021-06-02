package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.events.mission.NewMission;
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

    public void feed(BeginMatch event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(EndMatch event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(NewMission event) {
        box.mounter(MissionMounter.class).handle(event);
    }

    public void feed(NewStateMission event) {
        box.mounter(MissionMounter.class).handle(event);
    }
}
