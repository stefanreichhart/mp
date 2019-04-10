package com.mp.exception;

public class BadRequestException extends RuntimeException {

    private Object payload;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Object payload, String message) {
        super(message);
        this.payload = payload;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

}
