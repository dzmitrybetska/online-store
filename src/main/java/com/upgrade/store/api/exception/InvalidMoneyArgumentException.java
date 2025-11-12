package com.upgrade.store.api.exception;

public class InvalidMoneyArgumentException extends RuntimeException {

    public InvalidMoneyArgumentException(String message) {
        super(message);
    }

    public InvalidMoneyArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
