package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.graph.*;
import io.intino.magritte.framework.Layer;

import static io.intino.gamification.core.box.events.achievement.AchievementState.Pending;

public class AchievementMounter extends Mounter {

    public AchievementMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof CreateAchievement) handle((CreateAchievement) event);
        else if(event instanceof DeleteAchievement) handle((DeleteAchievement) event);
        else if(event instanceof AchievementNewState) handle((AchievementNewState) event);
    }

    private void handle(CreateAchievement event) {
        Achievement achievement = box.graph().achievement(event.id());
        if(achievement != null) return;

        Context context;
        if(event.type().equals(AchievementType.Local)) {
            context = box.graph().match(event.context());
        } else {
            context = box.graph().world(event.context());
        }
        if(context == null) return;

        achievement = box.graph().achievement(event, context);
        context.achievements().add(achievement);

        achievement.save$();
        context.save$();
    }

    private void handle(DeleteAchievement event) {
        Achievement achievement = box.graph().achievement(event.id());
        if(achievement == null) return;

        Context context = achievement.context();
        context.achievements().remove(achievement);
        context.save$();

        box.graph().achievementState(achievement.id()).forEach(Layer::delete$);

        achievement.delete$();
    }

    private void handle(AchievementNewState event) {
        Achievement achievement = box.graph().achievement(event.id());
        if(achievement == null) return;

        Player player = box.graph().player(event.player());
        if(player == null) return;

        AchievementState achievementState = box.graph().achievementState(achievement.id(), player.id());
        if(achievementState == null) {
            achievementState = box.graph().achievementState(event, achievement, player);

            Context context = achievement.context();
            if(context instanceof World) {
                player.achievements().add(achievementState);
                player.save$();
            } else {
                PlayerState playerState = box.graph().playerState(player, player.world().match());
                if(playerState == null) playerState = box.graph().playerState(player, player.world().match());
                playerState.achievements().add(achievementState);
                playerState.save$();
            }
        } else {
            if(achievementState.state().equals(Pending)) achievementState.state(event.state());
        }

        achievementState.save$();
    }
}
