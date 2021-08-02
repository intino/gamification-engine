package old.core.box.checkers;

import old.core.box.CoreBox;
import old.core.box.checkers.entries.MissionEntry;
import old.core.box.events.EventBuilder;
import old.core.box.events.GamificationEvent;
import old.core.box.helper.MissionStateHelper;
import old.core.graph.Mission;
import old.core.graph.MissionState;
import old.core.graph.Player;

import java.util.List;

import static old.core.model.attributes.MissionState.Cancelled;
import static old.core.model.attributes.MissionState.Completed;

public class MissionChecker extends Checker {

    public MissionChecker(CoreBox box) {
        super(box);
    }

    public void checkMissions(GamificationEvent event) {

        List<MissionEntry> missionEntries = box.graph().mission(event.getClass());

        missionEntries.forEach(me -> {
            me.players().forEach(p -> checkMission(event, me.mission(), me.world(), p));
        });
    }

    private void checkMission(GamificationEvent event, Mission mission, String worldId, Player player) {
        CheckResult checkResult = mission.check(event, player);
        if(checkResult == CheckResult.Skip) return;

        MissionState missionState = box.helper(MissionStateHelper.class).getOrCreateMissionState(mission, worldId, player);

        if(checkResult == CheckResult.Progress) {
            missionState.count(missionState.count() + 1).save$();
            if(missionState.count() >= mission.maxCount()) {
                box.terminal().feed(EventBuilder.newStateMission(worldId, mission.id(), player.id(), Completed));
            }
        } else if(checkResult == CheckResult.Cancel) {
            box.terminal().feed(EventBuilder.newStateMission(worldId, mission.id(), player.id(), Cancelled));
        }
    }
}