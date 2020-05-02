package com.bz.blueauth.exception;

public class NotAcceptableException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public NotAcceptableException(String msg) {
        super(msg);
    }
    public NotAcceptableException(Throwable cause) {
        super(cause);
    }
    public NotAcceptableException(String message, Throwable cause) {
        super(message, cause);
    }    
}