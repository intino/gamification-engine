package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.AchievementState;

import java.util.List;
import java.util.Map;

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
        //TODO FALTAN FILTROS
        Map<Achievement, List<AchievementState>> achievements = box.graph().achievement(event.getClass());

        achievements.forEach((achievement, achievementStates) -> {
            if(achievement.check(event)) {
                achievementStates.forEach(as -> as.progress(box));
            }
        });
    }

    protected abstract void mount(GamificationEvent event);
}
