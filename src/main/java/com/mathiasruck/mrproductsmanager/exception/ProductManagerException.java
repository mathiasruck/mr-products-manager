package com.mathiasruck.mrproductsmanager.exception;

public class ProductManagerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public ProductManagerException() {
        super();
    }

    public ProductManagerException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}