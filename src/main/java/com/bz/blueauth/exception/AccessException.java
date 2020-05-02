package com.bz.blueauth.exception;

public class AccessException extends RuntimeException {
    static final long serialVersionUID = 1L;
   
    public AccessException(String msg) {
        super(msg);
    }
    public AccessException(Throwable cause) {
        super(cause);
    }
    public AccessException(String message, Throwable cause) {
        super(message, cause);        
    }
}