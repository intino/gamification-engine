package io.intino.gamification.core.graph;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class World extends AbstractWorld {

	private static Function<Integer, Integer> levelFunction;
	private static Function<MissionState, Integer> missionScoreFunction;

	static {
		levelFunction(score -> Math.toIntExact(Math.max(1, Math.round(5.7 * Math.log(Math.max(score, 1)) - 24.5))));
		missionScoreFunction(missionState -> {
			World world = missionState.graph().world(missionState.worldId());
			if(world.match() != null) {
				Mission mission = missionState.graph().mission(world.match().missions(), missionState.missionId());
				return Math.round(100 * mission.difficulty().multiplier() * mission.type().multiplier() * missionState.state().multiplier());
			}
			return 0;
		});
	}

	public static void levelFunction(Function<Integer, Integer> levelFunction) {
		World.levelFunction = levelFunction;
	}

	public static void missionScoreFunction(Function<MissionState, Integer> missionScoreFunction) {
		World.missionScoreFunction = missionScoreFunction;
	}

	public static int levelOf(int score) {
		return levelFunction.apply(score);
	}

	public int scoreOf(MissionState missionState) {
		return missionScoreFunction.apply(missionState);
	}

	public World(io.intino.magritte.framework.Node node) {
		super(node);
	}

	@Override
	public List<Player> players() {
		//TODO
		return entities().stream().filter(e -> e instanceof Player).map(e -> (Player) e).collect(Collectors.toList());
	}
}