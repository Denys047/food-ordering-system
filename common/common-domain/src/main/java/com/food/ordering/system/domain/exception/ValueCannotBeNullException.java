package com.food.ordering.system.domain.exception;

public class ValueCannotBeNullException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "ID cannot be null. Please provide a valid ID value.";

    public ValueCannotBeNullException() {
        super(DEFAULT_MESSAGE);
    }

    public ValueCannotBeNullException(String message) {
        super(message);
    }

}
