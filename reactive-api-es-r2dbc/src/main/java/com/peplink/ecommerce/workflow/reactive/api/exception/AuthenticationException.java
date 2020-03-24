package com.peplink.ecommerce.workflow.reactive.api.exception;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(String debugMessage) {
        super(debugMessage);
    }

}
