package com.chandrakumar.ms.api.person.util;

public class PersonConstant {

    private PersonConstant() {
        throw new IllegalStateException("PersonConstant class");
    }

    public static final String PERSON_ID = "personId";
    public static final String EMAIL_ID = "emailId";
    public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    public static final String ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT = "The 'personId' is invalid UUID format";
    public static final String ERROR_PERSON_ID_IS_NOT_FOUND = "The 'personId' is not found";
    public static final String ERROR_THE_EMAIL_IS_ALREADY_EXISTS = "The 'email id' is already exists!!!";

    public static final String ERROR_NO_RECORD_FOUND = "No record found";
}

