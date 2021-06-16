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
		return 1;
		//return graph().engineConfig().playerLevelMapper().levelOf(this, score);
	}

	public Integer matchLevel() {
		return 1;
		/*World world = graph().world(worldId);
		Match match = world.match();
		if(match == null) return null;
		PlayerState playerState = match.playersState(ps -> ps.playerId().equals(id)).stream()
				.findFirst().orElse(null);
		if(playerState == null) {
			return 1;
		} else {
			return EngineConfiguration.levelOf(this, playerState.score());
		}*/
	}
}