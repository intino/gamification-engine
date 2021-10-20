package org.example.cinepolis.control.gamification.dispatcher;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.World;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.datahub.events.gamification.MissionEndChecker;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class MissionEndCheckerDispatcher extends Dispatcher<MissionEndChecker> {

    public MissionEndCheckerDispatcher(ControlBox box) {
        super(box);
    }

    @Override
    public void dispatch(MissionEndChecker event) {

        World world = box.adapter().world();

        Match match = world.currentMatch();
        if(match == null) return;

        for (Match.PlayerState playerState : match.players().values()) {
            check(playerState);
        }
    }

    private void check(Match.PlayerState playerState) {
        for (MissionAssignment missionAssignment : playerState.missionAssignments()) {
            if(missionAssignment.hasExpired() && missionAssignment.progress().state() == InProgress) {
                missionAssignment.fail();
            }
        }
    }
}
