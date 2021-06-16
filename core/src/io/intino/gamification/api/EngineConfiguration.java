package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.mission.MissionState;
import io.intino.gamification.core.box.logic.MissionScoreMapper;
import io.intino.gamification.core.box.logic.PlayerLevelMapper;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;

public class EngineConfiguration {

    private final CoreBox box;
    private PlayerLevelMapper playerLevelMapper;
    private MissionScoreMapper missionScoreMapper;

    public EngineConfiguration(CoreBox box) {
        this.box = box;
        playerLevelFunction((player, score) -> Math.toIntExact(Math.max(1, Math.round(5.7 * Math.log(Math.max(score, 1)) - 24.5))));
        missionScoreFunction((player, mission, state) -> Math.round(100 * mission.difficulty().multiplier() * mission.type().multiplier() * state.multiplier()));
    }

    public void playerLevelFunction(PlayerLevelMapper playerLevelMapper) {
        this.playerLevelMapper = playerLevelMapper;
    }

    public void missionScoreFunction(MissionScoreMapper missionScoreMapper) {
        this.missionScoreMapper = missionScoreMapper;
    }

    public int levelOf(Player player, int score) {
        return playerLevelMapper.level(player, score);
    }

    public int scoreOf(Player player, Mission mission, MissionState missionState) {
        return missionScoreMapper.score(player, mission, missionState);
    }
}
