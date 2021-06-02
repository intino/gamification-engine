package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.AchievementState;
import io.intino.magritte.framework.Layer;

import java.util.List;

public class AchievementMounter extends Mounter {

    public AchievementMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof CreateAchievement) handle((CreateAchievement) event);
        if(event instanceof ModifyAchievement) handle((ModifyAchievement) event);
        if(event instanceof DeleteAchievement) handle((DeleteAchievement) event);
        if(event instanceof AchievementNewStatus) handle((AchievementNewStatus) event);
    }

    protected void handle(CreateAchievement event) {

        Achievement achievement = box.graph().getAchievement(event.id());

        if(achievement != null) return;

        box.graph().achievement(event).save$();
    }

    protected void handle(ModifyAchievement event) {

        Achievement achievement = box.graph().getAchievement(event.id());

        if(achievement == null) return;

        achievement.type(event.type())
                .description(event.description())
                .save$();
    }

    protected void handle(DeleteAchievement event) {

        Achievement achievement = box.graph().getAchievement(event.id());

        if(achievement == null) return;

        List<AchievementState> states = box.graph().getAchievementStates(achievement.id());

        achievement.delete$();
        states.forEach(Layer::delete$);
    }

    protected void handle(AchievementNewStatus event) {

        Achievement achievement = box.graph().getAchievement(event.id());

        if(achievement == null) return;

        box.graph().achievementState(event).save$();
    }
}
