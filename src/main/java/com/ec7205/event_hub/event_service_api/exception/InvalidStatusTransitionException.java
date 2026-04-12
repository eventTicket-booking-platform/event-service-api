package com.ec7205.event_hub.event_service_api.exception;

public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(String message) {
        super(message);
    }
}
