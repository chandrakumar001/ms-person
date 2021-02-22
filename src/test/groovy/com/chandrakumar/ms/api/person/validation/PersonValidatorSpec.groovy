package com.chandrakumar.ms.api.person.validation

import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.person.service.PersonMockData.personBareDTO
import static com.chandrakumar.ms.api.person.util.PersonErrorCodeConstant.ERROR_EMAIL_ID_IS_INVALID_FORMAT
import static com.chandrakumar.ms.api.person.util.PersonErrorCodeConstant.ERROR_THE_AGE_MUST_BE_18
import static com.chandrakumar.ms.api.person.validation.PersonValidator.validatePersonDTO

@Unroll
class PersonValidatorSpec extends Specification {

    def "Failed::validatePersonDTO, personDTO Object"() {
        given: "a actualErrorMessage declaration"
        Optional<String> actualErrorMessage = null

        when: "call that validatePersonDTO() method"
        actualErrorMessage = validatePersonDTO(mockDBPersonData)

        then: "Verify the error message"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                          | mockDBPersonData                                               || expectedErrorMessage
        "The email is invalid format"     | personBareDTO("osaimar", "chandra", "kumar", "21")             || Optional.of(ERROR_EMAIL_ID_IS_INVALID_FORMAT)
        "The 'Age' is below 18"           | personBareDTO("osaimar19@gmail.com", "chandra", "kumar", "17") || Optional.of(ERROR_THE_AGE_MUST_BE_18)
        "The 'Age' exactly being 18"      | personBareDTO("osaimar19@gmail.com", "chandra", "kumar", "18") || Optional.empty()
        "All attribute value are correct" | personBareDTO("osaimar19@gmail.com", "chandra", "kumar", "21") || Optional.empty()
    }
}
