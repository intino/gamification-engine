package io.intino.gamification.api;

import io.intino.gamification.core.launcher.ParameterProcessor;
import io.intino.gamification.utils.time.Scale;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Configuration {

    public final Variable<String> timeZone;
    public final Variable<Integer> frameRate;
    public final Variable<String> gamificationPath;
    public final Variable<Scale> eventRegisterScale;

    public Configuration(ParameterProcessor parameterProcessor) {
        this.timeZone = new Variable<>(parameterProcessor.timeZone());
        this.frameRate = new Variable<>(Integer.parseInt(parameterProcessor.frameRate()));
        this.gamificationPath = new Variable<>(parameterProcessor.gamificationPath());
        this.eventRegisterScale = new Variable<>(Scale.valueOf(parameterProcessor.eventRegisterScale()));
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
