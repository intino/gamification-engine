package io.intino.gamification.core.box.helper;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.MissionState;
import io.intino.gamification.core.graph.Player;

import static io.intino.gamification.core.model.attributes.MissionState.Pending;

public class MissionStateHelper extends Helper {

    public MissionStateHelper(CoreBox box) {
        super(box);
    }

    public MissionState getOrCreateMissionState(Mission mission, String worldId, Player player) {
        MissionState missionState = box.graph().missionStateOf(mission.id(), player.id());
        if(missionState == null) {
            box.terminal().feed(EventBuilder.newStateMission(worldId, mission.id(), player.id(), Pending));
            missionState = box.graph().missionStateOf(mission.id(), player.id());
        }
        return missionState;
    }
}
