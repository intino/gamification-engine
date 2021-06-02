package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.events.attributes.MatchState;

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
}