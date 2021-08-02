package old.core.box.mounter;

import old.core.box.CoreBox;
import old.core.box.checkers.AchievementChecker;
import old.core.box.checkers.MissionChecker;
import old.core.box.events.GamificationEvent;

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
