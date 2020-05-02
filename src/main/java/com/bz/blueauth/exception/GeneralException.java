package com.bz.blueauth.exception;

public class GeneralException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public GeneralException(String msg) {        
        super(msg);
    }
    public GeneralException(Throwable cause) {
        super(cause);
    }
    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }    
}