package com.bz.blueauth.exception;

public class NotFoundException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public NotFoundException(String msg) {
        super(msg);
    }
    public NotFoundException(Throwable cause) {
        super(cause);
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }    
}