package old.core.graph;

import io.intino.magritte.framework.Node;

public abstract class Entity extends AbstractEntity {

	public static final double MIN_HEALTH = 0.0;
	public static final double MAX_HEALTH = 100.0;

	public Entity(Node node) {
		super(node);
	}
}