package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.AchievementState;
import io.intino.gamification.core.graph.Player;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static io.intino.gamification.core.box.events.achievement.AchievementState.Achieved;

public abstract class Mounter {

    protected final CoreBox box;

    public Mounter(CoreBox box) {
        this.box = box;
    }

    public void handle(GamificationEvent event) {
        checkAchievements(event);
        mount(event);
    }

    private void checkAchievements(GamificationEvent event) {

        Map<Achievement, List<Player>> achievements = box.graph().achievement(event.getClass());

        achievements.forEach((achievement, players) -> {
            players.forEach(player -> {
                if(achievement.check(event, player)) {
                    AchievementState achievementState = box.graph().achievementState(achievement.id(), player.id());
                    if(achievementState == null) {
                        achievementState = box.graph().achievementState(achievement, player);
                        achievementState.save$();
                    }

                    achievementState.count(achievementState.count() + 1).save$();
                    if(achievementState.count() >= achievement.maxCount()) {
                        AchievementNewState achievementNewState = new AchievementNewState();
                        achievementNewState.ts(Instant.now());
                        achievementNewState.id(achievement.id());
                        achievementNewState.state(Achieved);
                        achievementNewState.player(player.id());
                        box.engineTerminal().feed(achievementNewState);
                    }
                }
            });
        });
    }

    protected abstract void mount(GamificationEvent event);
}
