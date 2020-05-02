package com.bz.blueauth.exception;

import java.io.Serializable;
import java.util.List;

public class ErrorResponse implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    private boolean ok = false;
 
    //General error message about nature of error
    private String message;
 
    //Specific errors in API request processing
    private List<String> details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
    
    
    //Getter and setters
    
}