package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.Achievement;
import io.intino.gamification.core.box.events.AchievementNewStatus;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.graph.AchievementDefinition;

public class AchievementMounter extends Mounter {

    public AchievementMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof Achievement) handle((Achievement) event);
        if(event instanceof AchievementNewStatus) handle((AchievementNewStatus) event);
    }

    protected void handle(Achievement event) {

        AchievementDefinition achievementDefinition = box.graph().getAchievementDefinition(event.id());

        if(achievementDefinition == null) {

        } else {
            /*achievementDefinition.type(event.type())
                    .description(event.description())
                    .save$();*/
        }
    }

    protected void handle(AchievementNewStatus event) {

    }
}
