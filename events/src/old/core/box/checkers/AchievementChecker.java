package old.core.box.checkers;

import old.core.box.CoreBox;
import old.core.box.checkers.entries.AchievementEntry;
import old.core.box.events.EventBuilder;
import old.core.box.events.GamificationEvent;
import old.core.model.attributes.AchievementType;
import old.core.box.helper.AchievementStateHelper;
import old.core.graph.Achievement;
import old.core.graph.AchievementState;
import old.core.graph.Player;

import java.util.List;

import static old.core.model.attributes.AchievementState.Achieved;
import static old.core.model.attributes.AchievementState.Failed;

public class AchievementChecker extends Checker {

    public AchievementChecker(CoreBox box) {
        super(box);
    }

    public void checkAchievements(GamificationEvent event) {

        List<AchievementEntry> achievementEntries = box.graph().achievement(event.getClass());

        achievementEntries.forEach(ae -> {
            ae.players().forEach(p -> checkAchievement(event, ae.achievement(), ae.type(), ae.context(), p));
        });
    }

    private void checkAchievement(GamificationEvent event, Achievement achievement, AchievementType type, String contextId, Player player) {
        CheckResult checkResult = achievement.check(event, player);
        if(checkResult == CheckResult.Skip) return;

        AchievementState achievementState = box.helper(AchievementStateHelper.class).getOrCreateAchievementState(achievement, type, contextId, player);

        if(checkResult == CheckResult.Progress) {
            achievementState.count(achievementState.count() + 1).save$();
            if(achievementState.count() >= achievement.maxCount()) {
                box.terminal().feed(EventBuilder.newStateAchievement(achievement.id(), contextId, player.id(), Achieved, type));
            }
        } else if(checkResult == CheckResult.Cancel) {
            box.terminal().feed(EventBuilder.newStateAchievement(achievement.id(), contextId, player.id(), Failed, type));
        }
    }
}
