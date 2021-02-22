package com.chandrakumar.ms.api.person.util;

public class PersonErrorCodeConstant {

    private PersonErrorCodeConstant() {
        throw new IllegalStateException("PersonErrorCodeConstant class");
    }

    public static final String ERROR_EMAIL_ID_IS_INVALID_FORMAT = "Invalid.format.personBareDTO.EmailId";
    public static final String ERROR_THE_AGE_MUST_BE_18 = "Eligible.personBareDTO.Age";
    public static final String ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT = "Invalid.format.person.personId";
    public static final String ERROR_PERSON_ID_IS_NOT_FOUND = "NotFound.person.personId";
    public static final String ERROR_THE_EMAIL_IS_ALREADY_EXISTS = "AlreadyFound.person.email";
    public static final String ERROR_NO_RECORD_FOUND = "NoRecord.found.list";
}