package org.example.cinepolis.control.gamification.dispatcher;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.util.time.Scale;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.model.mission.CinepolisMission;
import org.example.cinepolis.datahub.events.gamification.CheckPenalties;

import java.time.Instant;

import static io.intino.gamification.util.data.Progress.State.InProgress;
import static io.intino.gamification.util.time.TimeUtils.currentInstant;
import static io.intino.gamification.util.time.TimeUtils.getInstantDiff;

public class CheckPenaltiesDispatcher extends Dispatcher<CheckPenalties> {

    private Instant now;

    public CheckPenaltiesDispatcher(ControlBox box) {
        super(box);
    }

    @Override
    public void dispatch(CheckPenalties event) {

        Match match = box.adapter().world().currentMatch();
        now = currentInstant();

        if(match == null) return;

        for (Match.PlayerState ps : match.players().values()) {
            ps.addScore(penaltyOf(ps));
        }
    }

    private int penaltyOf(Match.PlayerState playerState) {

        int penalty = 0;

        for (MissionAssignment ma : playerState.missionAssignments()) {
            penalty += penaltyOf(ma);
        }

        return penalty;
    }

    private int penaltyOf(MissionAssignment missionAssignment) {

        if(missionAssignment.enabled() && missionAssignment.progress().state() == InProgress) {
            return penaltyOf((CinepolisMission) missionAssignment.mission(), missionAssignment);
        }

        return 0;
    }

    private int penaltyOf(CinepolisMission mission, MissionAssignment missionAssignment) {
        int elapsedHours = (int) getInstantDiff(now, missionAssignment.creationTime(), Scale.Hour);
        return mission.penalizationAt(elapsedHours);
    }
}
