package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.entity.Action;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.graph.*;

import java.time.Instant;

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
        Mission mission = box.graph().mission(event.id());
        if(mission != null) return;

        Match match = box.graph().match(event.match());
        if(match == null) return;

        mission = box.graph().mission(event, match);
        match.missions().add(mission);

        mission.save$();
        match.save$();
    }

    private void handle(NewStateMission event) {
        Mission mission = box.graph().mission(event.id());
        if(mission == null) return;

        Player player = box.graph().player(event.player());
        if(player == null) return;

        PlayerState playerState = box.graph().playerState(player, player.world().match());
        if(playerState == null) {
            playerState = box.graph().playerState(player, player.world().match());
            playerState.save$();
        }

        MissionState missionState = box.graph().missionState(playerState.missionState(), mission.id());
        if(missionState == null) {
            missionState = box.graph().missionState(event, mission, player);
            playerState.missionState().add(missionState);
            playerState.save$();
        } else {
            if(missionState.state().equals(Pending)) missionState.state(event.state());
        }

        missionState.save$();

        changeScore(missionState, player);
    }

    private void changeScore(MissionState missionState, Player player) {
        Action action = (Action) Action.changeScore(player.id(), player.world().scoreOf(missionState))
                .ts(Instant.now());
        box.engineTerminal().feed(action);
    }
}
