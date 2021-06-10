package io.intino.gamification.test;

import io.intino.gamification.Engine;
import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.entity.PickUpItem;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.mission.MissionDifficulty;
import io.intino.gamification.core.box.events.mission.MissionType;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.logic.CheckerHandler;
import io.intino.gamification.core.graph.*;

import java.util.function.Function;

public class Test {

    public static void main(String[] args) {

        /*Engine engine = null;

        Entity.setAttributeListener("test", (entity, oldValue, newValue) -> oldValue, Integer::parseInt);

        CreateAchievement createAchievement = new CreateAchievement()
                .world("context")
                .description("description")
                .type(AchievementType.Global)
                .event(EventType.BeginMatch)
                .maxCount(0)
                .id("id1");

        engine.terminal().feed(createAchievement);

        Achievement achievement = engine.datamart().achievement("id1");
        if(achievement != null) achievement.progressIf((CheckerHandler.Checker<BeginMatch>) (event, player) -> true);

        NewMission newMission = new NewMission()
                .description("description")
                .type(MissionType.Primary)
                .match("match1")
                .event(EventType.PickUpItem)
                .difficulty(MissionDifficulty.Medium)
                .maxCount(3)
                .id("id2");

        engine.terminal().feed(newMission);

        Mission mission = engine.datamart().mission("id2");
        if(mission != null) mission.progressIf((CheckerHandler.Checker<PickUpItem>) (event, player) -> checkPlayerGetMedkit(engine, event, player.id()));*/
    }

    /*private static boolean checkPlayerGetMedkit(Engine engine, PickUpItem event, String playerId) {
        //return event.player().equals(playerId) && engine.datamart().item(event.id()).name().equals("Medkit");
        return true;
    }*/
}

