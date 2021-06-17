package io.intino.gamification.core.graph;

public class Player extends AbstractPlayer {

	public Player(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public int level() {
		return graph().engineConfig().playerLevelMapper.get().level(this, score);
	}

	public Integer matchLevel() {
		World world = graph().world(worldId);
		Match match = world.match();
		if(match == null) return null;
		PlayerState playerState = match.playersState(ps -> ps.playerId().equals(id)).stream()
				.findFirst().orElse(null);
		if(playerState == null) {
			return 1;
		} else {
			return graph().engineConfig().playerLevelMapper.get().level(this, playerState.score());
		}
	}
}