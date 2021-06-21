package io.intino.gamification.core.exception;

public class MissingEventAttributeException extends RuntimeException {

    public MissingEventAttributeException(String parameter) {
        super(parameter + " has not been initialized.");
    }
}
