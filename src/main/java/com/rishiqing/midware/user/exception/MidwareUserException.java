package com.rishiqing.midware.user.exception;

/**
 * Created by  on 2017/7/20.Wallace
 */
public class MidwareUserException extends RuntimeException {
    public MidwareUserException() {
    }

    public MidwareUserException(String message) {
        super(message);
    }

    public MidwareUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public MidwareUserException(Throwable cause) {
        super(cause);
    }

    public MidwareUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
