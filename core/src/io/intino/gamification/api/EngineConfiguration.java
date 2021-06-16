package io.intino.gamification.api;

import io.intino.gamification.core.box.events.mission.MissionState;
import io.intino.gamification.core.box.logic.MissionScoreMapper;
import io.intino.gamification.core.box.logic.PlayerLevelMapper;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;

public class EngineConfiguration {

    public final Variable<PlayerLevelMapper> playerLevelMapper = new Variable<>(EngineConfiguration::playerLevelMapper);
    public final Variable<MissionScoreMapper> missionScoreMapper = new Variable<>(EngineConfiguration::missionScoreMapper);

    public static final class Variable<T> {

        private T value;

        public Variable() {
            this(null);
        }

        public Variable(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public T getOrDefault(T defaultValue) {
            return value != null ? value : defaultValue;
        }

        public void set(T value) {
            this.value = value;
        }
    }

    private static int playerLevelMapper(Player player, int score) {
        return Math.toIntExact(Math.max(1, Math.round(5.7 * Math.log(Math.max(score, 1)) - 24.5)));
    }

    private static int missionScoreMapper(Player player, Mission mission, MissionState state) {
        return Math.round(100 * mission.difficulty().multiplier() * mission.type().multiplier() * state.multiplier());
    }
}
