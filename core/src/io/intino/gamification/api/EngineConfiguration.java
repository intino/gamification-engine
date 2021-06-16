package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.mission.MissionState;
import io.intino.gamification.core.box.logic.MissionScoreMapper;
import io.intino.gamification.core.box.logic.PlayerLevelMapper;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;

public class EngineConfiguration {

    private final CoreBox box;
    private static PlayerLevelMapper playerLevelMapper;
    private static MissionScoreMapper missionScoreMapper;

    public EngineConfiguration(CoreBox box) {
        this.box = box;
    }

    static {
        levelFunction((player, score) -> Math.toIntExact(Math.max(1, Math.round(5.7 * Math.log(Math.max(score, 1)) - 24.5))));
        missionScoreFunction((player, mission, state) -> Math.round(100 * mission.difficulty().multiplier() * mission.type().multiplier() * state.multiplier()));
    }

    public static void levelFunction(PlayerLevelMapper playerLevelMapper) {
        EngineConfiguration.playerLevelMapper = playerLevelMapper;
    }

    public static void missionScoreFunction(MissionScoreMapper missionScoreMapper) {
        EngineConfiguration.missionScoreMapper = missionScoreMapper;
    }

    public static int levelOf(Player player, int score) {
        return playerLevelMapper.level(player, score);
    }

    public static int scoreOf(Player player, Mission mission, MissionState missionState) {
        return missionScoreMapper.score(player, mission, missionState);
    }
}
