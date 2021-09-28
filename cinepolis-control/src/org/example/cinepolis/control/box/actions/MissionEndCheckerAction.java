package org.example.cinepolis.control.box.actions;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.data.Progress;
import org.example.cinepolis.control.box.ControlBox;
import io.intino.alexandria.exceptions.*;
import java.time.*;
import java.util.*;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class MissionEndCheckerAction {

	public ControlBox box;

	public void execute() {

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