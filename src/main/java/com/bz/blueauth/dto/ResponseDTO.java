package com.bz.blueauth.dto;


public class ResponseDTO<T> extends DataTransferObject{
    private boolean ok;
    private T response;
    private String access_token;

    

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public ResponseDTO(boolean ok, T response, String token) {
        this.ok = ok;
        this.response = response;
        this.access_token = token;
    }

    public ResponseDTO(T response, String token) {
        this.ok = true;
        this.response = response;
        this.access_token = token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    

    
}