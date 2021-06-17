package io.intino.gamification.core.box.helper;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.AchievementState;
import io.intino.gamification.core.graph.Player;

public class AchievementStateHelper extends Helper {

    public AchievementStateHelper(CoreBox box) {
        super(box);
    }

    public AchievementState getOrCreateAchievementState(Achievement achievement, AchievementType type, String contextId, Player player) {
        AchievementState achievementState = box.graph().achievementStateOf(achievement.id(), player.id());
        if(achievementState == null) {
            box.terminal().feed(EventBuilder.newStateAchievement(achievement.id(), contextId, player.id(), io.intino.gamification.core.box.events.achievement.AchievementState.Pending, type));
            achievementState = box.graph().achievementStateOf(achievement.id(), player.id());
        }
        return achievementState;
    }
}
