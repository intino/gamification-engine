package old.core.box.helper;

import old.core.box.CoreBox;
import old.core.box.events.EventBuilder;
import old.core.graph.Mission;
import old.core.graph.MissionState;
import old.core.graph.Player;

import static old.core.model.attributes.MissionState.Pending;

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
