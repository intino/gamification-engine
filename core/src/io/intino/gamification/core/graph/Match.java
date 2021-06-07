package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.match.MatchState;

import java.util.List;

public class Match extends AbstractMatch {

	public Match(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public MatchState state() {
		return MatchState.valueOf(stateName);
	}

	public Match state(MatchState state) {
		stateName(state.name());
		return this;
	}

	@Override
	public List<Player> players() {
		return world.players();
	}
}