package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.AchievementState;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.match.MatchState;
import io.intino.gamification.core.box.events.mission.MissionState;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.graph.*;

import java.time.Instant;
import java.util.List;

public class MatchMounter extends Mounter {

    public MatchMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof BeginMatch) handle((BeginMatch) event);
        if(event instanceof EndMatch) handle((EndMatch) event);
    }

    private void handle(BeginMatch event) {
        Match match = box.graph().match(event.id());
        if(match != null) return;

        World world = box.graph().world(event.world());
        if(world == null || world.match() != null) return;

        match = box.graph().match(event, world);
        world.match(match);

        match.save$();
        world.save$();
    }

    private void handle(EndMatch event) {

        Match match = box.graph().match(event.id());
        if(match == null) return;

        match.to(event.ts()).state(MatchState.Finished).save$();

        World world = match.world();
        if(world.match().id().equals(match.id())) world.match(null).save$();

        fail(match);
    }

    private void fail(Match match) {
        match.players().forEach(p -> {
            failMissions(match.missions(), p.id());
            failAchievements(match.achievements(), p.id());
        });
    }

    private void failMissions(List<Mission> missions, String playerId) {
        missions.forEach(m -> box.engineTerminal().feed(getNewStateMission(m.id(), playerId)));
    }

    private void failAchievements(List<Achievement> achievements, String playerId) {
        achievements.forEach(a -> box.engineTerminal().feed(getNewStateAchievement(a.id(), playerId)));
    }

    private NewStateMission getNewStateMission(String missionId, String playerId) {
        NewStateMission newStateMission = new NewStateMission();
        newStateMission.ts(Instant.now());
        newStateMission.id(missionId);
        newStateMission.player(playerId);
        newStateMission.state(MissionState.Failed);
        return newStateMission;
    }

    private AchievementNewState getNewStateAchievement(String achievementId, String playerId) {
        AchievementNewState achievementNewState = new AchievementNewState();
        achievementNewState.ts(Instant.now());
        achievementNewState.id(achievementId);
        achievementNewState.player(playerId);
        achievementNewState.state(AchievementState.Failed);
        return achievementNewState;
    }
}
