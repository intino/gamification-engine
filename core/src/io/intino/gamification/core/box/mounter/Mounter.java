package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.graph.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static io.intino.gamification.core.box.events.achievement.AchievementState.Achieved;
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
                if(achievement.check(event, player)) {
                    AchievementState achievementState = box.graph().achievementState(achievement.id(), player.id());
                    if(achievementState == null) {
                        achievementState = box.graph().achievementState(achievement.id(), player.id());
                        achievementState.save$();
                    }

                    achievementState.count(achievementState.count() + 1).save$();
                    if(achievementState.count() >= achievement.maxCount()) {
                        box.engineTerminal().feed(EventBuilder.newStateAchievement(achievement.id(), player.id(), Achieved));
                    }
                }
            });
        });
    }

    private void checkMissions(GamificationEvent event) {

        Map<String, Map<Mission, List<Player>>> missions = box.graph().mission(event.getClass());

        missions.forEach((worldId, value) -> {
            value.forEach((mission, players) -> {
                players.forEach(player -> {
                    if(mission.check(event, player)) {
                        MissionState missionState = box.graph().missionStateOf(mission.id(), player.id());
                        if(missionState == null) {
                            missionState = box.graph().missionState(player.worldId(), mission.id(), player.id());
                            missionState.save$();
                        }

                        missionState.count(missionState.count() + 1).save$();
                        if(missionState.count() >= mission.maxCount()) {
                            box.engineTerminal().feed(EventBuilder.newStateMission(worldId, mission.id(), player.id(), Completed));
                        }
                    }
                });
            });
        });
    }

    protected abstract void mount(GamificationEvent event);
}
