package io.intino.gamification.core.box.checkers;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.MissionState;
import io.intino.gamification.core.graph.Player;

import java.util.List;
import java.util.Map;

import static io.intino.gamification.core.box.events.mission.MissionState.*;

public class MissionChecker extends Checker {

    public MissionChecker(CoreBox box) {
        super(box);
    }

    public void checkMissions(GamificationEvent event) {

        Map<String, Map<Mission, List<Player>>> missions = box.graph().mission(event.getClass());

        missions.forEach((worldId, worldMissions) -> {
            worldMissions.forEach((mission, players) -> {
                players.forEach(player -> {
                    CheckResult checkResult = mission.check(event, player);
                    if(checkResult != CheckResult.Skip) {
                        MissionState missionState = box.graph().missionStateOf(mission.id(), player.id());
                        if(missionState == null) {
                            box.engineTerminal().feed(EventBuilder.newStateMission(worldId, mission.id(), player.id(), Pending));
                            missionState = box.graph().missionStateOf(mission.id(), player.id());
                        }

                        if(checkResult == CheckResult.Progress) {
                            missionState.count(missionState.count() + 1).save$();
                            if(missionState.count() >= mission.maxCount()) {
                                box.engineTerminal().feed(EventBuilder.newStateMission(worldId, mission.id(), player.id(), Completed));
                            }
                        } else if(checkResult == CheckResult.Cancel) {
                            box.engineTerminal().feed(EventBuilder.newStateMission(worldId, mission.id(), player.id(), Cancelled));
                        }
                    }
                });
            });
        });
    }
}
