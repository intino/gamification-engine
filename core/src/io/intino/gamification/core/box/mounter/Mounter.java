package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.logic.CheckResult;
import io.intino.gamification.core.graph.*;

import java.util.List;
import java.util.Map;

import static io.intino.gamification.core.box.events.achievement.AchievementState.Achieved;
import static io.intino.gamification.core.box.events.achievement.AchievementState.Failed;
import static io.intino.gamification.core.box.events.mission.MissionState.Cancelled;
import static io.intino.gamification.core.box.events.mission.MissionState.Completed;

public abstract class Mounter {

    protected final CoreBox box;

    public Mounter(CoreBox box) {
        this.box = box;
    }

    public void handle(GamificationEvent event) {
        checkAchievements(event);
        checkMissions(event);
        mount(event);
    }

    private void checkAchievements(GamificationEvent event) {

        Map<Achievement, List<Player>> achievements = box.graph().achievement(event.getClass());

        achievements.forEach((achievement, players) -> {
            players.forEach(player -> {
                CheckResult checkResult = achievement.check(event, player);
                if(checkResult != CheckResult.Skip) {
                    AchievementState achievementState = box.graph().achievementStateOf(achievement.id(), player.id());
                    if(achievementState == null) {
                        achievementState = box.graph().achievementState(achievement.id(), player.id());
                        achievementState.save$();
                    }

                    if(checkResult == CheckResult.Progress) {
                        achievementState.count(achievementState.count() + 1).save$();
                        if(achievementState.count() >= achievement.maxCount()) {
                            box.engineTerminal().feed(EventBuilder.newStateAchievement(achievement.id(), player.id(), Achieved));
                        }
                    } else if(checkResult == CheckResult.Cancel) {
                        box.engineTerminal().feed(EventBuilder.newStateAchievement(achievement.id(), player.id(), Failed));
                    }
                }
            });
        });
    }

    private void checkMissions(GamificationEvent event) {

        Map<String, Map<Mission, List<Player>>> missions = box.graph().mission(event.getClass());

        missions.forEach((worldId, worldMissions) -> {
            worldMissions.forEach((mission, players) -> {
                players.forEach(player -> {
                    CheckResult checkResult = mission.check(event, player);
                    if(checkResult != CheckResult.Skip) {
                        MissionState missionState = box.graph().missionStateOf(mission.id(), player.id());
                        if(missionState == null) {
                            missionState = box.graph().missionState(player.worldId(), mission.id(), player.id());
                            missionState.save$();
                        }

                        if(checkResult == CheckResult.Progress) {
                            missionState.count(missionState.count() + 1).save$();
                            if(missionState.count() >= mission.maxCount()) {
                                box.engineTerminal().feed(EventBuilder.newStateMission(worldId, mission.id(), player.id(), Completed));
                            }
                        } else if(checkResult == CheckResult.Cancel) {
                            box.engineTerminal().feed(EventBuilder.newStateMission(worldId, mission.id(), player.id(), Cancelled));
                        }
                    }
                });
            });
        });
    }

    protected abstract void mount(GamificationEvent event);
}
