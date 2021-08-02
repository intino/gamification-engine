package old.core.box.helper;

import old.core.box.CoreBox;
import old.core.box.events.EventBuilder;
import old.core.model.attributes.AchievementType;
import old.core.graph.Achievement;
import old.core.graph.AchievementState;
import old.core.graph.Player;

public class AchievementStateHelper extends Helper {

    public AchievementStateHelper(CoreBox box) {
        super(box);
    }

    public AchievementState getOrCreateAchievementState(Achievement achievement, AchievementType type, String contextId, Player player) {
        AchievementState achievementState = box.graph().achievementStateOf(achievement.id(), player.id());
        if(achievementState == null) {
            box.terminal().feed(EventBuilder.newStateAchievement(achievement.id(), contextId, player.id(), old.core.model.attributes.AchievementState.Pending, type));
            achievementState = box.graph().achievementStateOf(achievement.id(), player.id());
        }
        return achievementState;
    }
}
