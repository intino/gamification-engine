package io.intino.gamification.api;

import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.MissionState;
import io.intino.gamification.core.graph.World;

import java.util.function.Function;

public class EngineConfiguration {

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
        EngineConfiguration.levelFunction = levelFunction;
    }

    public static void missionScoreFunction(Function<MissionState, Integer> missionScoreFunction) {
        EngineConfiguration.missionScoreFunction = missionScoreFunction;
    }

    public static int levelOf(int score) {
        return levelFunction.apply(score);
    }

    public static int scoreOf(MissionState missionState) {
        return missionScoreFunction.apply(missionState);
    }
}
