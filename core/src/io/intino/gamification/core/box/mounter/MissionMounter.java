package io.intino.gamification.core.box.mounter;

import io.intino.gamification.api.EngineConfiguration;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.mounter.builder.MissionFilter;
import io.intino.gamification.core.graph.*;

import static io.intino.gamification.core.box.events.mission.MissionState.Pending;

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
        MissionFilter filter = new MissionFilter(box, event);
        if(!filter.newMissionCanMount()) return;

        Match match = filter.match();
        Mission mission = box.graph().mission(event);

        match.missions().add(mission);

        match.save$();
        mission.save$();
    }

    private void handle(NewStateMission event) {
        MissionFilter filter = new MissionFilter(box, event);
        if(!filter.newStateMissionCanMount()) return;

        World world = filter.world();
        Match match = filter.match();
        Player player = filter.player();
        Mission mission = filter.mission();

        PlayerState playerState = box.graph().playerState(match.playersState(), player.id());
        if(playerState == null) {
            playerState = box.graph().playerState(player.id());
            match.playersState().add(playerState);
        }

        MissionState missionState = box.graph().missionState(playerState.missionState(), mission.id());
        if(missionState == null) {
            missionState = box.graph().missionState(event, world.id(), mission.id(), player.id());
            playerState.missionState().add(missionState);
        } else {
            if(missionState.state().equals(Pending)) {
                missionState.state(event.state());
            }
        }

        box.engineTerminal().feed(EventBuilder.changeScore(world.id(), player.id(), EngineConfiguration.scoreOf(missionState)));

        match.save$();
        playerState.save$();
        missionState.save$();
    }
}