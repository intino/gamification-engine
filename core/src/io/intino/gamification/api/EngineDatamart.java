package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.graph.AchievementDefinition;
import io.intino.gamification.core.graph.Entity;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.Mission;

import java.util.List;

public class EngineDatamart {

    private final CoreBox box;

    public EngineDatamart(CoreBox box) {
        this.box = box;
    }

    public List<Entity> entities() {
        return box.graph().entityList();
    }

    public List<Mission> missions() {
        return box.graph().missionList();
    }

    public List<AchievementDefinition> achievementDefinitions() {
        return box.graph().achievementDefinitionList();
    }

    public List<Match> matches() {
        return box.graph().matchList();
    }
}
