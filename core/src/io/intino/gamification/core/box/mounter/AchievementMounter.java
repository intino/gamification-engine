package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.AchievementNewStatus;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.ModifyAchievement;
import io.intino.gamification.core.graph.Achievement;

public class AchievementMounter extends Mounter {

    public AchievementMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof ModifyAchievement) handle((ModifyAchievement) event);
        if(event instanceof AchievementNewStatus) handle((AchievementNewStatus) event);
    }

    protected void handle(ModifyAchievement event) {

        Achievement achievementDefinition = box.graph().getAchievementDefinition(event.id());

        if(achievementDefinition == null) {
            box.graph().achivementDefinition(event).save$();
        } else {
            achievementDefinition.type(event.type())
                    .description(event.description())
                    .save$();
        }
    }

    protected void handle(AchievementNewStatus event) {

        Achievement achievementDefinition = box.graph().getAchievementDefinition(event.id());

        if(achievementDefinition == null) return;

        box.graph().achievementChecked(event).save$();
    }
}
