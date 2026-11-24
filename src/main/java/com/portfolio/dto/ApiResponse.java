package com.portfolio.dto;

import java.util.Map;

public class ApiResponse {

    private boolean success;
    private String message;
    private Map<String, String> errors;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, Map<String, String> errors) {
        this.success = success;
        this.message = message;
        this.errors = errors;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Map<String, String> getErrors() { return errors; }
}
