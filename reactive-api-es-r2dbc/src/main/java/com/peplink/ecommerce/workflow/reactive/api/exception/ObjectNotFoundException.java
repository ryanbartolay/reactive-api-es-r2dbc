package com.peplink.ecommerce.workflow.reactive.api.exception;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String debugMessage) {
        super(debugMessage);
    }

}