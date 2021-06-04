package io.intino.gamification.core.graph;

import static io.intino.gamification.core.box.events.helper.Math.*;

public class Player extends AbstractPlayer {

	public Player(io.intino.magritte.framework.Node node) {
		super(node);
		this.attributesMap.put("score", new AttributeHandler() {
			@Override
			public void set(Object value) {
				score(asInt(value));
			}

			@Override
			public String get() {
				return String.valueOf(score());
			}
		});
	}
}