package io.intino.gamification.core.box.checkers;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.checkers.entries.MissionEntry;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.helper.MissionStateHelper;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.MissionState;
import io.intino.gamification.core.graph.Player;

import java.util.List;

import static io.intino.gamification.core.model.attributes.MissionState.Cancelled;
import static io.intino.gamification.core.model.attributes.MissionState.Completed;

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