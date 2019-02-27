package com.mx.exception;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class IPQueryException extends RuntimeException {

    public IPQueryException() {
    }

    public IPQueryException(String message) {
        super(message);
    }

    public IPQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public IPQueryException(Throwable cause) {
        super(cause);
    }

    public IPQueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
