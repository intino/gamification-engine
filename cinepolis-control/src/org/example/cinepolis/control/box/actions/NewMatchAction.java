package org.example.cinepolis.control.box.actions;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.time.TimeUtils;
import org.example.cinepolis.control.box.ControlBox;

import java.util.Map;

public class NewMatchAction {

	public ControlBox box;

	public void execute() {
		World world = box.adapter().world();
		Map<String, Match.PlayerState> persistencePlayerState = world.currentMatch().persistencePlayerState();
		world.finishCurrentMatch();
		world.startNewMatch(new Match(world.id(), getMatchId(), persistencePlayerState));
	}

	private String getMatchId() {
		StringBuilder sb = new StringBuilder()
				.append("match")
				.append("_")
				.append(TimeUtils.currentInstant().toString());

		return sb.toString();
	}
}