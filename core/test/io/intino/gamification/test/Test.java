package io.intino.gamification.test;

import io.intino.gamification.Engine;
import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.logic.CheckerHandler;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.Entity;

public class Test {

    public static void main(String[] args) {

        Engine engine = null;

        Entity.setAttributeListener("test", (entity, oldValue, newValue) -> oldValue, Integer::parseInt);

        CreateAchievement createAchievement = new CreateAchievement()
                .context("context")
                .description("description")
                .type(AchievementType.Global)
                .event(EventType.BeginMatch)
                .maxCount(0)
                .id("id1");

        engine.terminal().feed(createAchievement);

        Achievement achievement = engine.datamart().achievement("id1");
        if(achievement != null) achievement.progressIf((CheckerHandler.Checker<BeginMatch>) (event, player) -> true);
    }
}

