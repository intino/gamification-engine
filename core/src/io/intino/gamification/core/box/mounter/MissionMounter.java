package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.entity.EntityType;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.graph.*;

public class MissionMounter extends Mounter {

    public MissionMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof NewMission) handle((NewMission) event);
        if(event instanceof NewStateMission) handle((NewStateMission) event);
    }

    private void handle(NewMission event) {
        if(box.graph().existsMission(event.id())) return;

        Match match = box.graph().match(event.match());
        if(match == null) return;

        Mission mission = box.graph().mission(event);
        match.missions().add(mission);

        mission.save$();
        match.save$();
    }

    private void handle(NewStateMission event) {
        Entity player = box.graph().entity(event.player());
        Mission mission = box.graph().mission(event.mission());
        if(player == null || mission == null) return;
        if(!player.type().equals(EntityType.Player)) return;

        Match match = player.world().match();
        if(match == null) return;

        EntityState entityState = match.entitiesState().stream()
                .filter(es -> es.player().id().equals(event.player()))
                .findFirst().orElse(null);

        if(entityState == null) entityState = box.graph().entityState(player);

        MissionState missionState = entityState.missionState().stream()
                .filter(ms -> ms.mission().id().equals(event.mission()))
                .findFirst().orElse(null);

        if(missionState != null) {
            missionState.state(event.state());
            missionState.save$();
        } else {
            missionState = box.graph().missionState(event, mission);
        }

        entityState.missionState().add(missionState);
        entityState.save$();
    }
}
