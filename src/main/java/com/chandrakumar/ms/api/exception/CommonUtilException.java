package com.chandrakumar.ms.api.exception;

public class CommonUtilException {

    private CommonUtilException() {
        throw new IllegalStateException("CommonException class");
    }

    public static void fieldValidationException(final String errorMessage) {
        throw new FieldValidationException(errorMessage);
    }

    public static void httpHeaderExceptionException(final String errorMessage) {
        throw new HttpHeaderException(errorMessage);
    }
}
