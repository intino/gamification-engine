package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.checkers.AchievementChecker;
import io.intino.gamification.core.box.checkers.MissionChecker;
import io.intino.gamification.core.box.events.GamificationEvent;

public abstract class Mounter {

    protected final CoreBox box;

    public Mounter(CoreBox box) {
        this.box = box;
    }

    public void handle(GamificationEvent event) {
        box.checker(AchievementChecker.class).checkAchievements(event);
        box.checker(MissionChecker.class).checkMissions(event);
        mount(event);
    }

    protected abstract void mount(GamificationEvent event);
}
