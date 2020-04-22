package com.example.demo.core.exception;

public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 346441748363053186L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
