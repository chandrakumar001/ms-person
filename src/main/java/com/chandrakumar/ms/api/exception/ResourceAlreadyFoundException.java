package com.chandrakumar.ms.api.exception;

public class ResourceAlreadyFoundException extends RuntimeException {

    public ResourceAlreadyFoundException(final String msg) {
        super(msg);
    }
}
