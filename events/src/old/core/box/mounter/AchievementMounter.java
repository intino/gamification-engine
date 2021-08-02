package old.core.box.mounter;

import old.core.box.CoreBox;
import old.core.box.events.GamificationEvent;
import old.core.box.events.achievement.AchievementNewState;
import old.core.box.events.achievement.CreateAchievement;
import old.core.box.events.achievement.DeleteAchievement;
import old.core.box.mounter.filter.AchievementFilter;
import old.core.graph.*;
import io.intino.magritte.framework.Layer;

import static old.core.model.attributes.AchievementState.Pending;

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
        AchievementFilter filter = new AchievementFilter(box, event);
        if(!filter.canMount()) return;

        Context context = filter.context();
        Achievement achievement = box.graph().achievement(event);

        context.achievements().add(achievement);

        context.save$();
        achievement.save$();
    }

    private void handle(DeleteAchievement event) {
        AchievementFilter filter = new AchievementFilter(box, event);
        if(!filter.canMount()) return;

        Context context = filter.context();
        Achievement achievement = filter.achievement();

        context.achievements().remove(achievement);
        box.graph().achievementState(achievement.id()).forEach(Layer::delete$);

        context.save$();
        achievement.delete$();
    }

    private void handle(AchievementNewState event) {
        AchievementFilter filter = new AchievementFilter(box, event);
        if(!filter.canMount()) return;

        Context context = filter.context();
        Achievement achievement = filter.achievement();
        Player player = filter.player();

        AchievementState achievementState = box.graph().achievementStateOf(achievement.id(), player.id());
        if(achievementState == null) {
            achievementState = box.graph().achievementState(event, achievement.id(), player.id());
            if(context instanceof World) {
                player.achievements().add(achievementState);
                player.save$();
            } else {
                PlayerState playerState = box.graph().playerState(((Match) context).playersState(), player.id());
                if(playerState == null) playerState = box.graph().playerState(player.id());
                playerState.achievements().add(achievementState);
                playerState.save$();
            }
        } else {
            if(achievementState.state().equals(Pending)) achievementState.state(event.state());
        }

        achievementState.save$();
    }
}
