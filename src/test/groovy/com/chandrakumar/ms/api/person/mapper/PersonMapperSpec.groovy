package com.chandrakumar.ms.api.person.mapper

import com.chandrakumar.ms.api.person.mapper.PersonMapper
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class PersonMapperSpec extends Specification {

    def "Failed::PersonMapper,Object creation"() {

        given: "a PersonMapper declaration"
        String errorMessage = null
        when: ""
        try {
            new PersonMapper();
        } catch (Exception e) {
            errorMessage = e.getMessage()
        }

        then: "Verify the error message"
        errorMessage == 'PersonMapper class'
    }
}
