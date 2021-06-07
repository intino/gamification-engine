package io.intino.gamification.core.graph;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class World extends AbstractWorld {

	private Function<Integer, Integer> levelFunction;

	public World(io.intino.magritte.framework.Node node) {
		super(node);
		levelFunction(score -> Math.toIntExact(Math.max(1, Math.round(5.7 * Math.log(Math.max(score, 1)) - 24.5))));
	}

	@Override
	public List<Player> players() {
		//TODO
		return entities().stream().filter(e -> e instanceof Player).map(e -> (Player) e).collect(Collectors.toList());
	}

	public World levelFunction(Function<Integer, Integer> levelFunction) {
		this.levelFunction = levelFunction;
		return this;
	}

	public int getLevelOf(int score) {
		return levelFunction.apply(score);
	}
}