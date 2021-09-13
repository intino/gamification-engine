package io.intino.gamification.util;

import io.intino.gamification.api.Configuration;

import java.util.logging.Level;

public final class Log {

    public static final Configuration.Variable<Logger> LoggerInstance = new Configuration.Variable<>(Log.Logger.getDefault());

    public static void debug(String message) {
        LoggerInstance.get().debug(message);
    }

    public static void info(String message) {
        LoggerInstance.get().info(message);
    }

    public static void warn(String message) {
        LoggerInstance.get().warn(message);
    }

    public static void error(Throwable e) {
        error(e.getMessage(), e);
    }

    public static void error(String message) {
        LoggerInstance.get().error(message);
    }

    public static void error(String message, Throwable e) {
        LoggerInstance.get().error(message, e);
    }

    private Log() {}

    public interface Logger {

        void debug(String message);
        void info(String message);
        void warn(String message);
        void error(String message);
        void error(String message, Throwable e);

        static Logger getDefault() {
            return new Logger() {

                private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("GamificationEngine");

                @Override
                public void debug(String message) {
                    logger.fine(message);
                }

                @Override
                public void info(String message) {
                    logger.info(message);
                }

                @Override
                public void warn(String message) {
                    logger.warning(message);
                }

                @Override
                public void error(String message) {
                    logger.severe(message);
                }

                @Override
                public void error(String message, Throwable e) {
                    logger.log(Level.SEVERE, message, e);
                }
            };
        }
    }
}
