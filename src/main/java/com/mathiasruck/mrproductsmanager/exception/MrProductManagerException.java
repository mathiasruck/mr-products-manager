package com.mathiasruck.mrproductsmanager.exception;

public class MrProductManagerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public MrProductManagerException() {
        super();
    }

    public MrProductManagerException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}