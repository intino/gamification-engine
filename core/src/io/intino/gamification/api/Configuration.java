package io.intino.gamification.api;

import io.intino.gamification.core.launcher.ParameterProcessor;
import io.intino.gamification.utils.time.Scale;

public class Configuration {

    public final Variable<String> timeZone;
    public final Variable<String> gamificationPath;

    public Configuration(ParameterProcessor parameterProcessor) {
        this.timeZone = new Variable<>(parameterProcessor.timeZone());
        this.gamificationPath = new Variable<>(parameterProcessor.gamificationPath());
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
