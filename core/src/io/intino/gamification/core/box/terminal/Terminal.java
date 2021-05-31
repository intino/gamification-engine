package io.intino.gamification.core.box.terminal;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.mounter.AchievementMounter;
import io.intino.gamification.core.box.mounter.EntityMounter;
import io.intino.gamification.core.box.mounter.MatchMounter;
import io.intino.gamification.core.box.mounter.MissionMounter;

public class Terminal {

    private final CoreBox box;

    public Terminal(CoreBox box) {
        this.box = box;
    }

    public void handle(Achievement event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void handle(AchievementStatus event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void handle(Action event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void handle(AttachEntity event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void handle(DestroyEntity event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void handle(DetachEntity event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void handle(Entity event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void handle(MatchBegin event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void handle(MatchEnd event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void handle(Mission event) {
        box.mounter(MissionMounter.class).handle(event);
    }

    public void handle(MissionStatus event) {
        box.mounter(MissionMounter.class).handle(event);
    }
}
