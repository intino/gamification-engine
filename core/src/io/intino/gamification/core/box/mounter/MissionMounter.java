package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;
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

    protected void handle(NewMission event) {

        Match match = box.graph().match(event.match());

        if(match == null) return;

        Mission mission = box.graph().mission(event);
        match.missions().add(mission);

        mission.save$();
        match.save$();
    }

    protected void handle(NewStateMission event) {

        EntityState playerState = box.graph().entityState(event.player());

        if(playerState == null) {
            playerState = box.graph().entityState(event);
            playerState.save$();
        }

        MissionState missionState = playerState.missionState(m -> m.mission().id().equals(event.mission())).stream()
                .findFirst().orElse(null);

        if(missionState == null) {
            missionState = box.graph().missionState(event);
        }

        missionState.state(event.state()).save$();
    }
}
