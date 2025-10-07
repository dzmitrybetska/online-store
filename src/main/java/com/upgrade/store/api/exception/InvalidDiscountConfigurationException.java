package com.upgrade.store.api.exception;

public class InvalidDiscountConfigurationException extends RuntimeException {

    public InvalidDiscountConfigurationException(String message) {
        super(message);
    }

    public InvalidDiscountConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
