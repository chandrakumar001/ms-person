package com.chandrakumar.ms.api.person.validation;

import com.chandrakumar.ms.api.person.dto.PersonDTO;

import java.util.Optional;

import static com.chandrakumar.ms.api.util.CommonUtil.notValidateEmail;

public class PersonValidator {

    private static final int DEFAULT_AGE_ELIGIBLE = 18;
    private static final String ERROR_EMAIL_ID_IS_INVALID_FORMAT = "The 'EmailId' is invalid format";
    private static final String ERROR_THE_AGE_MUST_BE_18 = "The 'Age' must be 18";

    private PersonValidator() {
        throw new IllegalStateException("PersonValidator class");
    }

    public static Optional<String> validatePersonDTO(final PersonDTO personDTO) {

        if (notValidateEmail(personDTO.getEmailId())) {
            return Optional.of(ERROR_EMAIL_ID_IS_INVALID_FORMAT);
        }

        if (DEFAULT_AGE_ELIGIBLE > Integer.parseInt(personDTO.getAge())) {
            return Optional.of(ERROR_THE_AGE_MUST_BE_18);
        }
        return Optional.empty();
    }
}
