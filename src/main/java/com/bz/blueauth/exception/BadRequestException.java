package com.bz.blueauth.exception;

public class BadRequestException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public BadRequestException(String msg) {
        super(msg);
    }
    public BadRequestException(Throwable cause) {
        super(cause);
    }
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}