package io.tetrabot.util;

import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public final class Log {

    private static final AtomicReference<Logger> LoggerInstance = new AtomicReference<>(Log.Logger.getDefault());
    public static void setLogger(Logger logger) {
        requireNonNull(logger);
        LoggerInstance.set(logger);
    }

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

                @Override
                public void debug(String message) {
                    io.intino.alexandria.logger.Logger.debug(message);
                }

                @Override
                public void info(String message) {
                    io.intino.alexandria.logger.Logger.info(message);
                }

                @Override
                public void warn(String message) {
                    io.intino.alexandria.logger.Logger.warn(message);
                }

                @Override
                public void error(String message) {
                    io.intino.alexandria.logger.Logger.error(message);
                }

                @Override
                public void error(String message, Throwable e) {
                    io.intino.alexandria.logger.Logger.error(message, e);
                }
            };
        }
    }
}
