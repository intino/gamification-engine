package io.intino.gamification.api;

import io.intino.gamification.core.graph.MissionState;

import java.util.function.Function;

public class EngineConfiguration {

    public final Variable<Function<Integer, Integer>> levelFunction = new Variable<>();
    public final Variable<Function<MissionState, Integer>> missionScoreFunction = new Variable<>();

    public EngineConfiguration() {
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
}
