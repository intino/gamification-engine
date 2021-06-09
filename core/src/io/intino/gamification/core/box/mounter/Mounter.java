package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.graph.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

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
                        box.engineTerminal().feed(getAchievementNewState(achievement.id(), player.id()));
                    }
                }
            });
        });
    }

    private void checkMissions(GamificationEvent event) {

        Map<Mission, List<Player>> missions = box.graph().mission(event.getClass());

        missions.forEach((mission, players) -> {
            players.forEach(player -> {
                if(mission.check(event, player)) {
                    MissionState missionState = box.graph().missionStateOf(mission.id(), player.id());
                    if(missionState == null) {
                        missionState = box.graph().missionState(mission.id(), player.id());
                        missionState.save$();
                    }

                    missionState.count(missionState.count() + 1).save$();
                    if(missionState.count() >= mission.maxCount()) {
                        box.engineTerminal().feed(getMissionNewState(mission.id(), player.id()));
                    }
                }
            });
        });
    }

    protected abstract void mount(GamificationEvent event);

    private AchievementNewState getAchievementNewState(String achievementId, String playerId) {
        AchievementNewState achievementNewState = new AchievementNewState();
        achievementNewState.ts(Instant.now());
        achievementNewState.id(achievementId);
        achievementNewState.state(io.intino.gamification.core.box.events.achievement.AchievementState.Achieved);
        achievementNewState.player(playerId);
        return achievementNewState;
    }

    private NewStateMission getMissionNewState(String missionId, String playerId) {
        NewStateMission newStateMission = new NewStateMission();
        newStateMission.ts(Instant.now());
        newStateMission.id(missionId);
        newStateMission.state(io.intino.gamification.core.box.events.mission.MissionState.Completed);
        newStateMission.player(playerId);
        return newStateMission;
    }
}
