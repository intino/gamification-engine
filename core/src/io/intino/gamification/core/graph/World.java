package io.intino.gamification.core.graph;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class World extends AbstractWorld {

	private Function<Integer, Integer> levelFunction;
	private Function<MissionState, Integer> missionScoreFunction;

	public World(io.intino.magritte.framework.Node node) {
		super(node);
		levelFunction(score -> Math.toIntExact(Math.max(1, Math.round(5.7 * Math.log(Math.max(score, 1)) - 24.5))));
		missionScoreFunction(missionState -> {
			Mission mission = missionState.mission();
			return 100 * mission.difficulty().multiplier() * mission.type().multiplier() * missionState.state().multiplier();
		});
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

	public World missionScoreFunction(Function<MissionState, Integer> missionScoreFunction) {
		this.missionScoreFunction = missionScoreFunction;
		return this;
	}

	public int getLevelOf(int score) {
		return levelFunction.apply(score);
	}

	public int scoreOf(MissionState missionState) {
		return missionScoreFunction.apply(missionState);
	}
}