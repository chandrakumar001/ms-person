package com.chandrakumar.ms.api.person.validation


import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.person.service.PersonMockData.personDTO
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
        testStep                          | mockDBPersonData                                           || expectedErrorMessage
        "The email is invalid format"     | personDTO("osaimar", "chandra", "kumar", "21")             || Optional.of("The 'EmailId' is invalid format")
        "The 'Age' is below 18"           | personDTO("osaimar19@gmail.com", "chandra", "kumar", "17") || Optional.of("The 'Age' must be 18")
        "The 'Age' exactly being 18"      | personDTO("osaimar19@gmail.com", "chandra", "kumar", "18") || Optional.empty()
        "All attribute value are correct" | personDTO("osaimar19@gmail.com", "chandra", "kumar", "21") || Optional.empty()
    }
}
