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
    public void mount(GamificationEvent event) {
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
        Player player = box.graph().player(event.player());
        Mission mission = box.graph().mission(event.mission());
        if(player == null || mission == null) return;

        Match match = player.world().match();
        if(match == null) return;

        PlayerState playerState = box.graph().playerState(match.playersState(), event.player());
        if(playerState == null) playerState = box.graph().playerState(player, match);

        MissionState missionState = box.graph().missionState(playerState.missionState(), event.mission());
        if(missionState == null) missionState = box.graph().missionState(event, mission);

        missionState.state(event.state()).save$();
        playerState.missionState().add(missionState);
        playerState.save$();
    }
}
