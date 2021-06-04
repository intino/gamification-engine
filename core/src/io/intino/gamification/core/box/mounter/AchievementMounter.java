package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.graph.*;

public class AchievementMounter extends Mounter {

    public AchievementMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof CreateAchievement) handle((CreateAchievement) event);
        else if(event instanceof DeleteAchievement) handle((DeleteAchievement) event);
        else if(event instanceof AchievementNewState) handle((AchievementNewState) event);
    }

    private void handle(CreateAchievement event) {
        Achievement achievement = box.graph().achievement(event.id());
        if(achievement != null) return;

        achievement = box.graph().achievement(event);
        if(event.type().equals(AchievementType.Local)) {
            Match match = box.graph().match(event.context());
            if(match == null) return;
            match.achievements().add(achievement);
            match.save$();
        } else {
            World world = box.graph().world(event.context());
            if(world == null) return;
            world.achievements().add(achievement);
            world.save$();
        }
        achievement.save$();
    }

    private void handle(DeleteAchievement event) {
        Achievement achievement = box.graph().achievement(event.id());
        if(achievement == null) return;
        if(achievement.type().equals(AchievementType.Local)) {
            Match match = box.graph().match(achievement.context());
            match.achievements().remove(achievement);
            match.playersState().forEach(ps -> {
                AchievementState achievementState = box.graph().achievementState(ps.achievements(), achievement.id());
                if(achievementState != null) achievementState.delete$();
            });
            match.save$();
        } else {
            World world = box.graph().world(achievement.context());
            world.achievements().remove(achievement);
            //TODO
            world.entities(e -> e instanceof Player).stream().
                    map(e -> (Player) e)
                    .forEach(p -> {
                        AchievementState achievementState = box.graph().achievementState(p.achievements(), achievement.id());
                        if(achievementState != null) achievementState.delete$();
                    });
            world.save$();
        }
        achievement.delete$();
    }

    private void handle(AchievementNewState event) {
        Achievement achievement = box.graph().achievement(event.id());
        if(achievement == null) return;
        if(achievement.type().equals(AchievementType.Local)) {
            Match match = box.graph().match(achievement.context());
            PlayerState playerState = box.graph().playerState(match.playersState(), event.player());
            if(playerState == null) return;
            AchievementState achievementState = box.graph().achievementState(event, achievement);
            playerState.achievements().add(achievementState);
            playerState.save$();
        } else {
            World world = box.graph().world(achievement.context());
            Player player = box.graph().player(world.entities(), event.player());
            if(player == null) return;
            AchievementState achievementState = box.graph().achievementState(event, achievement);
            player.achievements().add(achievementState);
            player.save$();
        }
    }
}
