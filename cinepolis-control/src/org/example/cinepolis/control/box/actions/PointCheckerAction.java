package org.example.cinepolis.control.box.actions;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.graph.model.MissionAssignment;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.model.mission.CinesaMission;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class PointCheckerAction {

	public ControlBox box;

	public void execute() {

		Match match = box.adapter().world().currentMatch();

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
			return penaltyOf((CinesaMission) missionAssignment.mission());
		}

		return 0;
	}

	private int penaltyOf(CinesaMission mission) {
		return - mission.dailyPenalty();
	}
}