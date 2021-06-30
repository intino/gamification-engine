package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.mounter.filter.MissionFilter;
import io.intino.gamification.core.graph.*;

import static io.intino.gamification.core.model.attributes.MissionState.Pending;

public class MissionMounter extends Mounter {

    public MissionMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof CreateMission) handle((CreateMission) event);
        if(event instanceof NewStateMission) handle((NewStateMission) event);
    }

    private void handle(CreateMission event) {
        MissionFilter filter = new MissionFilter(box, event);
        if(!filter.canMount()) return;

        Match match = filter.match();
        Mission mission = box.graph().mission(event);

        match.missions().add(mission);

        match.save$();
        mission.save$();
    }

    private void handle(NewStateMission event) {
        MissionFilter filter = new MissionFilter(box, event);
        if(!filter.canMount()) return;

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

        int score = box.engineConfig().missionScoreMapper.get().score(player, mission, missionState.state());
        box.terminal().feed(EventBuilder.changeScore(world.id(), player.id(), score));

        match.save$();
        playerState.save$();
        missionState.save$();
    }
}