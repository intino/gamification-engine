package old.core.graph;

import old.core.model.attributes.MatchState;

import java.util.List;
import java.util.stream.Collectors;

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
		return graph().world(worldId).players();
	}

	public List<Mission> activeMissions() {
		return missions.stream().filter(Mission::isActive).collect(Collectors.toList());
	}
}