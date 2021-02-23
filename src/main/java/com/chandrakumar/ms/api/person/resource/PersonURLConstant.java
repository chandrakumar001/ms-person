package com.chandrakumar.ms.api.person.resource;

public class PersonURLConstant {

    private PersonURLConstant() {
        throw new IllegalStateException("PersonURLConstant class");
    }

    public static final String PERSON_ID_PATH = "person-id";
    public static final String V1_PERSONS_PERSON_ID_URL = "v1/people/{person-id}";
    public static final String V1_PERSONS_URL = "v1/people";
}
