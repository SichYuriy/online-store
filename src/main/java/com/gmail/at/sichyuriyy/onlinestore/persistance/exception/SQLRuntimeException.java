package com.gmail.at.sichyuriyy.onlinestore.persistance.exception;

/**
 * Created by Yuriy on 3/28/2017.
 */
public class SQLRuntimeException extends RuntimeException {

    public SQLRuntimeException() {
    }

    public SQLRuntimeException(String message) {
        super(message);
    }

    public SQLRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLRuntimeException(Throwable cause) {
        super(cause);
    }

    public SQLRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
