package old.api;

import old.core.box.CoreBox;
import old.core.box.configurator.GameLoopConfigurator;
import old.core.box.listeners.EntityAttributeListener;
import old.core.box.mappers.MissionScoreMapper;
import old.core.box.mappers.PlayerLevelMapper;
import old.core.graph.Entity;
import old.core.graph.Mission;
import old.core.graph.Player;
import old.core.model.attributes.MissionState;

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
