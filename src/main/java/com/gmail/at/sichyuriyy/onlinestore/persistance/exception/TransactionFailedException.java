package com.gmail.at.sichyuriyy.onlinestore.persistance.exception;

/**
 * Created by Yuriy on 4/13/2017.
 */
public class TransactionFailedException extends RuntimeException {

    public TransactionFailedException() {
        super();
    }

    public TransactionFailedException(String message) {
        super(message);
    }

    public TransactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionFailedException(Throwable cause) {
        super(cause);
    }

    protected TransactionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
