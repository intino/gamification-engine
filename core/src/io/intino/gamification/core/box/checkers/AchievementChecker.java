package io.intino.gamification.core.box.checkers;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.checkers.entries.AchievementEntry;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.model.attributes.AchievementType;
import io.intino.gamification.core.box.helper.AchievementStateHelper;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.AchievementState;
import io.intino.gamification.core.graph.Player;

import java.util.List;

import static io.intino.gamification.core.model.attributes.AchievementState.Achieved;
import static io.intino.gamification.core.model.attributes.AchievementState.Failed;

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
