package io.intino.gamification.core.graph;

import static io.intino.gamification.core.box.helper.Math.asInt;

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

	public int level() {
		return world.getLevelOf(score);
	}

	public Integer matchLevel() {
		Match match = world.match();
		if(match == null) return null;
		PlayerState playerState = match.playersState(ps -> ps.player().id().equals(id)).stream()
				.findFirst().orElse(null);
		if(playerState == null) {
			return 1;
		} else {
			return world.getLevelOf(playerState.score());
		}
	}
}