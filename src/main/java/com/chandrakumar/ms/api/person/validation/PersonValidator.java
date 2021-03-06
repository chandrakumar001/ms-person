package com.chandrakumar.ms.api.person.validation;

import com.chandrakumar.ms.api.person.swagger.model.PersonBareDTO;

import java.util.Optional;

import static com.chandrakumar.ms.api.person.util.PersonErrorCodeConstant.*;
import static com.chandrakumar.ms.api.util.CommonUtil.notValidateEmail;

public class PersonValidator {

    private PersonValidator() {
        throw new IllegalStateException("PersonValidator class");
    }

    private static final int DEFAULT_AGE_ELIGIBLE = 18;

    public static Optional<String> validatePersonDTO(final PersonBareDTO personBareDTO) {

        if (notValidateEmail(personBareDTO.getEmailId())) {
            return Optional.of(ERROR_EMAIL_ID_IS_INVALID_FORMAT);
        }

        try {
            Integer.parseInt(personBareDTO.getAge());
        } catch (NumberFormatException e) {
            return Optional.of(NUMBER_FORMAT_EXCEPTION_OCCURRED);
        }
        if (DEFAULT_AGE_ELIGIBLE > Integer.parseInt(personBareDTO.getAge())) {
            return Optional.of(ERROR_THE_AGE_MUST_BE_18);
        }
        return Optional.empty();
    }
}
