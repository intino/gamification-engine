package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.Achievement;
import io.intino.gamification.core.box.events.AchievementStatus;
import io.intino.gamification.core.box.events.GamificationEvent;

public class AchievementMounter extends Mounter {

    public AchievementMounter(CoreBox box) {
        super(box);
    }

    @Override
    protected void handle(GamificationEvent event) {
        if(event instanceof Achievement) handle((Achievement) event);
        if(event instanceof AchievementStatus) handle((AchievementStatus) event);
    }

    protected void handle(Achievement event) {

    }

    protected void handle(AchievementStatus event) {

    }
}
