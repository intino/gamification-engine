package io.intino.gamification.core.graph;

import java.util.List;

public abstract class Context extends AbstractContext {

	public Context(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public abstract List<Player> players();
}