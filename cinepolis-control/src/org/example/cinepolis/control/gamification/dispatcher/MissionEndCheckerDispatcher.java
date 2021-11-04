package org.example.cinepolis.control.gamification.dispatcher;

import io.intino.gamification.graph.model.Round;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.Competition;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.datahub.events.gamification.MissionEndChecker;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class MissionEndCheckerDispatcher extends Dispatcher<MissionEndChecker> {

    public MissionEndCheckerDispatcher(ControlBox box) {
        super(box);
    }

    @Override
    public void dispatch(MissionEndChecker event) {

        Competition competition = box.adapter().world();

        Round round = competition.currentSeason();
        if(round == null) return;

        for (Round.Match match : round.players().values()) {
            check(match);
        }
    }

    private void check(Round.Match match) {
        for (MissionAssignment missionAssignment : match.missionAssignments()) {
            if(missionAssignment.hasExpired() && missionAssignment.progress().state() == InProgress) {
                missionAssignment.fail();
            }
        }
    }
}
