package com.chandrakumar.ms.api.person.service

import com.chandrakumar.ms.api.person.dto.PersonDTO
import com.chandrakumar.ms.api.person.entity.Person
import com.chandrakumar.ms.api.person.repository.PersonRepository
import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.person.service.PersonMockData.person
import static com.chandrakumar.ms.api.person.service.PersonMockData.personDTO
import static com.chandrakumar.ms.api.person.util.PersonConstant.*

@Unroll
class PersonCommandServiceSpec extends Specification {

    private PersonCommandService commandService
    def personRepository = Mock(PersonRepository)

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    def setup() {
        commandService = new SimplePersonCommandService(personRepository)
    }

    def "Success::person IDs are logged whenever they are saved in the DB"() {
        given: "a person dao that assigns an ID to person"

        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final PersonDTO personDTO = personDTO(emailId, firstName, lastName, age)
        final Person person = person(emailId, firstName, lastName, age)

        personRepository.findByEmailId(_ as String) >> Optional.empty()
        personRepository.save(_ as Person) >> person

        when: "that person is saved in the DB"
        PersonDTO createdPersonDTO = commandService.createPerson(personDTO)

        then: "the ID is correctly logged"
        createdPersonDTO.emailId == emailId
        createdPersonDTO.age == age
        and:
        1 * personRepository.save(_ as Person) >> person
    }

    def "Failed::person IDs are logged whenever they are saved in the DB"() {
        given: "a person dao that assigns an ID to person"

        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"
        def actualErrorMessage = null

        final PersonDTO personDTO = personDTO(emailId, firstName, lastName, age)
        personRepository.findByEmailId(_ as String) >> Optional.of(mockDBPersonData)

        when: "that person is saved in the DB"
        try {
            commandService.createPerson(personDTO)
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                              | emailId               | mockDBPersonData                                        || expectedErrorMessage
        "Email id already exists in database" | "osaimar19@gmail.com" | person("osaimar19@gmail.com", "chandra", "kumar", "21") || ERROR_THE_EMAIL_IS_ALREADY_EXISTS

    }

    def "Success::updatePerson IDs are logged whenever they are saved in the DB"() {
        given: "a person dao that assigns an ID to person"
        def personId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final PersonDTO personDTO = personDTO(emailId, firstName, lastName, age)
        final Person person = person(emailId, firstName, lastName, age)

        personRepository.findById(_ as UUID) >> Optional.of(person)
        personRepository.save(_ as Person) >> person

        when: "that person is saved in the DB"
        PersonDTO createdPersonDTO = commandService.updatePerson(personId, personDTO)

        then: "the ID is correctly logged"

        createdPersonDTO.emailId == emailId
        createdPersonDTO.age == age
    }

    def "Failed::updatePerson IDs are logged whenever they are saved in the DB"() {
        given: "a person dao that assigns an ID to person"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"
        def actualErrorMessage = null

        final PersonDTO personDTO = personDTO(emailId, firstName, lastName, age)
        personRepository.findById(_ as UUID) >> mockDBPersonData

        when: "that person is saved in the DB"
        try {
            commandService.updatePerson(personId, personDTO)
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                      | personId                               | mockDBPersonData || expectedErrorMessage
        "The personId invalid format" | "d6f02a17-c676-4b1b-ae39"              | Optional.empty() || ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT
        "The person is not found"     | "d6f02a17-c676-4b1b-ae39-e3b12f47c407" | Optional.empty() || ERROR_PERSON_ID_IS_NOT_FOUND
    }

    def "Success::deletePerson IDs are logged whenever they are saved in the DB"() {
        given: "a person dao that assigns an ID to person"
        def personId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final Person person = person(emailId, firstName, lastName, age)
        personRepository.findById(_ as UUID) >> Optional.of(person)
        personRepository.deleteById(_ as UUID) >> null

        when: "that person is saved in the DB"
        commandService.deletePerson(personId)

        then: "the ID is correctly logged"
        1 * personRepository.deleteById(_ as UUID) >> null
    }

    def "Failed::deletePerson IDs are logged whenever they are saved in the DB"() {
        given: "a person dao that assigns an ID to person"

        def actualErrorMessage = null
        personRepository.findById(_ as UUID) >> mockDBPersonData

        when: "that person is saved in the DB"
        try {
            commandService.deletePerson(personId)
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                      | personId                               | mockDBPersonData || expectedErrorMessage
        "The personId invalid format" | "d6f02a17-c676-4b1b-ae39"              | Optional.empty() || ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT
        "The person is not found"     | "d6f02a17-c676-4b1b-ae39-e3b12f47c407" | Optional.empty() || ERROR_PERSON_ID_IS_NOT_FOUND
    }
}
