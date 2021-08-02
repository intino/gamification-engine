package io.intino.gamification.utils.event;

import io.intino.gamification.events.GamificationEvent;
import io.intino.gamification.events.achievement.CreateAchievement;
import io.intino.gamification.events.entity.*;
import io.intino.gamification.events.match.BeginMatch;
import io.intino.gamification.events.match.EndMatch;
import io.intino.gamification.events.mission.CreateMission;
import io.intino.gamification.events.world.CreateWorld;
import io.intino.gamification.events.world.DestroyWorld;
import io.intino.gamification.utils.Util;

public class EventUtils extends Util {

    public Class<? extends GamificationEvent> classOf(String type) {
        //TODO REGISTRAR TODOS LOS EVENTOS
        if(type.equals("CreateAchievement")) return CreateAchievement.class;
        if(type.equals("CreateItem")) return CreateItem.class;
        if(type.equals("CreateNpc")) return CreateNpc.class;
        if(type.equals("CreatePlayer")) return CreatePlayer.class;
        if(type.equals("DestroyItem")) return DestroyItem.class;
        if(type.equals("DestroyNpc")) return DestroyNpc.class;
        if(type.equals("DestroyPlayer")) return DestroyPlayer.class;
        if(type.equals("BeginMatch")) return BeginMatch.class;
        if(type.equals("EndMatch")) return EndMatch.class;
        if(type.equals("CreateMission")) return CreateMission.class;
        if(type.equals("CreateWorld")) return CreateWorld.class;
        if(type.equals("DestroyWorld")) return DestroyWorld.class;
        return null;
    }
}
