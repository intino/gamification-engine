package io.intino.gamification.core.graph;

import java.util.List;
import java.util.stream.Collectors;

public class World extends AbstractWorld {

	public World(io.intino.magritte.framework.Node node) {
		super(node);
	}

	@Override
	public List<Player> players() {
		//TODO
		return entities().stream().filter(e -> e instanceof Player).map(e -> (Player) e).collect(Collectors.toList());
	}
}