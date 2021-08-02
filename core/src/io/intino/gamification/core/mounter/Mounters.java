package io.intino.gamification.core.mounter;

import io.intino.gamification.core.Core;
import io.intino.gamification.events.GamificationEvent;
import io.intino.gamification.events.achievement.CreateAchievement;
import io.intino.gamification.events.entity.*;
import io.intino.gamification.events.match.BeginMatch;
import io.intino.gamification.events.match.EndMatch;
import io.intino.gamification.events.mission.CreateMission;
import io.intino.gamification.events.world.CreateWorld;
import io.intino.gamification.events.world.DestroyWorld;

import java.util.HashMap;
import java.util.Map;

public class Mounters {

    private final Core core;

    private static Map<Class<? extends GamificationEvent>, Mounter> builder = new HashMap<>();

    public Mounters(Core core) {
        this.core = core;
        buildMounters();
    }

    private void buildMounters() {
        //builder.put(TimeUtils.class, new TimeUtils(core.configuration().timeZone.get()));
    }

    public void mount(Class<? extends GamificationEvent> clazz, GamificationEvent event) {
        builder.get(clazz).mount(event);
    }
}
