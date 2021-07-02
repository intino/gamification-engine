package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.configurator.GameLoopConfigurator;
import io.intino.gamification.core.model.attributes.MissionState;
import io.intino.gamification.core.box.listeners.EntityAttributeListener;
import io.intino.gamification.core.box.mappers.*;
import io.intino.gamification.core.box.utils.TimeUtils;
import io.intino.gamification.core.graph.Entity;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;

public class EngineConfiguration {

    private final CoreBox box;
    public final GameLoopConfigurator gameLoopConfigurator;
    public final Variable<PlayerLevelMapper> playerLevelMapper = new Variable<>(EngineConfiguration::playerLevelMapper);
    public final Variable<MissionScoreMapper> missionScoreMapper = new Variable<>(EngineConfiguration::missionScoreMapper);
    public final Variable<EntityAttributeListener<Double>> healthListener = new Variable<>(EngineConfiguration::healthListener);
    public final Variable<EntityAttributeListener<Integer>> scoreListener = new Variable<>(EngineConfiguration::scoreListener);
    public final Variable<EntityAttributeListener<Boolean>> enableListener = new Variable<>(EngineConfiguration::enableListener);

    public EngineConfiguration(CoreBox box) {
        this.box = box;
        this.gameLoopConfigurator = new GameLoopConfigurator(box);
    }

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

    private static Double healthListener(Entity entity, Double oldValue, Double newValue) {
        return newValue;
    }

    private static Integer scoreListener(Entity entity, Integer oldValue, Integer newValue) {
        return newValue;
    }

    private static Boolean enableListener(Entity entity, Boolean oldValue, Boolean newValue) {
        return newValue;
    }
}
