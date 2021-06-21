package io.intino.gamification.core.exception;

public class InvalidAttributeValueException extends RuntimeException {

    public InvalidAttributeValueException(String attribute, String value, String attributeValue) {
        super(attribute + " value has been " + value + ". " + attributeValue);
    }
}
