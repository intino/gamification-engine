package io.intino.gamification.core;

public class Configuration {

    public final Variable<String> timeZone;
    public final Variable<String> gamificationPath;

    public Configuration(GamificationParameters gamificationParameters) {
        this.timeZone = new Variable<>(gamificationParameters.timeZone());
        this.gamificationPath = new Variable<>(gamificationParameters.gamificationPath());
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
