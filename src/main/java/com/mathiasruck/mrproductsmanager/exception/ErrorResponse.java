package com.mathiasruck.mrproductsmanager.exception;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ErrorResponse {
    private String message;

    public ErrorResponse(MrProductManagerException ex) {
        this.message = isNotBlank(ex.getMessage()) ? ex.getMessage() : "unknown_error";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}